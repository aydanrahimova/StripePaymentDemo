package com.example.stripepaymentdemo.service;

import com.example.stripepaymentdemo.dto.request.CardRequest;
import com.example.stripepaymentdemo.dto.response.CardResponse;
import com.stripe.exception.StripeException;

import java.util.List;

public interface CardService {
    List<CardResponse> getCardsOfUser(Long userId);

    CardResponse addCard(CardRequest request) throws StripeException;

    void deleteCard(Long id, Long userId);
}
