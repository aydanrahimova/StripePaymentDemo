package com.example.stripepaymentdemo.service.impl;

import com.example.stripepaymentdemo.dto.request.CardRequest;
import com.example.stripepaymentdemo.dto.response.CardResponse;
import com.example.stripepaymentdemo.entity.CreditCard;
import com.example.stripepaymentdemo.entity.User;
import com.example.stripepaymentdemo.mapper.CardMapper;
import com.example.stripepaymentdemo.repository.CreditCardRepository;
import com.example.stripepaymentdemo.repository.UserRepository;
import com.example.stripepaymentdemo.service.CardService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardServiceImpl implements CardService {
    UserRepository userRepository;
    CreditCardRepository cardRepository;
    CardMapper cardMapper;

    @Override
    public List<CardResponse> getCardsOfUser(Long userId) {
        //of course, it must be secured, but we focus only on payment integration in this project
        log.info("Operation of getting card of user with ID {} started", userId);
        User user = userRepository.findById(userId).orElseThrow();
        List<CreditCard> cards = cardRepository.findAllByUser(user);
        List<CardResponse> cardResponses = cards.stream().map(cardMapper::toDto).toList();
        log.info("Card of user with ID {} successfully returned", userId);
        return cardResponses;
    }

    @Override
    public CardResponse addCard(CardRequest request) throws StripeException {
        //that is user for testing,but in real frontend would call Stripe Elements or SDKs to generate paymentMethodId securely from the browser
        //without exposing card numbers to your backend.
        //backend only receives paymentMethodId
        //even for testing stripe doesn't allow card information be sent to backend openly-because it is unsafe
        log.info("Operation of adding card started for user with ID {}", request.getUserId());
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        //Create PaymentMethod with Stripe test token(testing)
        PaymentMethod paymentMethod = PaymentMethod.create(Map.of(
                "type", "card",
                "card", Map.of("token", "tok_visa")
        ));

        paymentMethod.attach(Map.of("customer", user.getStripeCustomerId()));

        //extract card details to store in db
        PaymentMethod.Card card = paymentMethod.getCard();

        CreditCard savedCard = new CreditCard();
        savedCard.setPaymentMethodId(paymentMethod.getId());
        savedCard.setBrand(card.getBrand());
        savedCard.setLast4(card.getLast4());
        savedCard.setExpMonth(card.getExpMonth());
        savedCard.setExpYear(card.getExpYear());
        savedCard.setUser(user);
        cardRepository.save(savedCard);
        CardResponse cardResponse = cardMapper.toDto(savedCard);
        log.info("Card successfully added");
        return cardResponse;
    }

    @Override
    public void deleteCard(Long id, Long userId) {
        CreditCard creditCard = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (!creditCard.getUser().getId().equals(userId)) {
            throw new RuntimeException("Card doesn't belong to the user");
        }

        try {
            PaymentMethod paymentMethod = PaymentMethod.retrieve(creditCard.getPaymentMethodId());

            // Detach the card (deletes it from Stripe)
            paymentMethod.detach();
        } catch (StripeException e) {
            throw new RuntimeException("Error while detaching card from Stripe: " + e.getMessage(), e);
        }

        cardRepository.delete(creditCard);

    }
}
