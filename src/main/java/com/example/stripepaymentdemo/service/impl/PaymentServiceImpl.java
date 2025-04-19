package com.example.stripepaymentdemo.service.impl;

import com.example.stripepaymentdemo.dto.request.PaymentRequest;
import com.example.stripepaymentdemo.dto.response.PaymentResponse;
import com.example.stripepaymentdemo.dto.request.ProductRequest;
import com.example.stripepaymentdemo.dto.response.StripeResponse;
import com.example.stripepaymentdemo.entity.CreditCard;
import com.example.stripepaymentdemo.entity.User;
import com.example.stripepaymentdemo.repository.CreditCardRepository;
import com.example.stripepaymentdemo.repository.UserRepository;
import com.example.stripepaymentdemo.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    CreditCardRepository creditCardRepository;
    UserRepository userRepository;

    @Override
    public StripeResponse checkoutProducts(ProductRequest productRequest) {

        log.info("Creating Stripe session for product: {}", productRequest.getProductName());

        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(productRequest.getProductName())
                .build();
        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(productRequest.getCurrency() == null ? "USD" : productRequest.getCurrency())
                .setUnitAmount(productRequest.getAmount() * 100)
                .setProductData(productData)
                .build();
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(productRequest.getQuantity())
                .setPriceData(priceData)
                .build();
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .addLineItem(lineItem)
                .build();
        Session session = null;
        try {
            log.info("Stripe session created successfully.");
            session = Session.create(params);
        } catch (StripeException e) {
            log.error("StripeException occurred while creating session: {}", e.getMessage(), e);
            throw new RuntimeException("Stripe session creation failed", e);
        }
        return StripeResponse.builder()
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .message("Payment session created")
                .status("Success")
                .build();
    }

    @Override
    public PaymentResponse chargeUser(PaymentRequest request) /*throws StripeException*/ {
        CreditCard savedCard = creditCardRepository.findById(request.getCardId())
                .orElseThrow(() -> new RuntimeException("Card not found"));
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        if (!savedCard.getUser().getId().equals(request.getUserId())) {
            throw new RuntimeException("Card doesn't belong to user");
        }

        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setAmount(request.getAmount() * 100)//convert cents to dollars
                .setCurrency("usd")
                .setCustomer(user.getStripeCustomerId())
                .setPaymentMethod(savedCard.getPaymentMethodId())
                .setConfirm(true)
                .setOffSession(true)
                .build();

        PaymentIntent paymentIntent = null;
        try {
            paymentIntent = PaymentIntent.create(createParams);
        } catch (StripeException e) {
            log.error("StripeException occurred while charging the user: {}", e.getMessage(), e);
            throw new RuntimeException("Payment failed", e);
        }

        return new PaymentResponse(paymentIntent.getId(), paymentIntent.getStatus(), paymentIntent.getAmount() / 100); // or .getStatus()
    }

}
