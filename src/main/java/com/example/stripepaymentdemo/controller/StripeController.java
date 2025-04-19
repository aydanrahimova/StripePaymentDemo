package com.example.stripepaymentdemo.controller;

import com.example.stripepaymentdemo.dto.ProductRequest;
import com.example.stripepaymentdemo.dto.StripeResponse;
import com.example.stripepaymentdemo.service.StripeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stripe")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StripeController {
    StripeService stripeService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkout(@RequestBody ProductRequest productRequest) {
        try {
            return new ResponseEntity<>(stripeService.checkoutProducts(productRequest), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
