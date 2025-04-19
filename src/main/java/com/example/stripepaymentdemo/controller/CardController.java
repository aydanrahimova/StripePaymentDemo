package com.example.stripepaymentdemo.controller;

import com.example.stripepaymentdemo.dto.request.CardRequest;
import com.example.stripepaymentdemo.dto.response.CardResponse;
import com.example.stripepaymentdemo.service.CardService;
import com.stripe.exception.StripeException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardController {
    CardService cardService;

    @GetMapping
    public List<CardResponse> getCardsOfUser(@RequestParam Long userId) {
        return cardService.getCardsOfUser(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CardResponse addCard(@RequestBody CardRequest request) throws StripeException {
        return cardService.addCard(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id, @RequestParam Long userId) {
        cardService.deleteCard(id,userId);
    }

}
