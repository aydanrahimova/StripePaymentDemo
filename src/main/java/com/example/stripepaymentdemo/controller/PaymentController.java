package com.example.stripepaymentdemo.controller;

import com.example.stripepaymentdemo.dto.request.PaymentRequest;
import com.example.stripepaymentdemo.dto.response.PaymentResponse;
import com.example.stripepaymentdemo.dto.request.ProductRequest;
import com.example.stripepaymentdemo.dto.response.StripeResponse;
import com.example.stripepaymentdemo.service.PaymentService;
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
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;

    /**
     * Checkout endpoint: creates a Stripe Checkout session for purchasing a product.
     * - Returns a session URL that typically redirects the user to Stripe's hosted checkout page.
     * - The user is required to enter their card information directly on Stripe’s page for each purchase.
     * - Once the payment is completed, the user is redirected back to your application (success or cancel URL).
     * This flow is suitable when you do not store payment methods and want Stripe to handle the entire checkout UI.
     */

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkout(@RequestBody ProductRequest productRequest) {
        try {
            return new ResponseEntity<>(paymentService.checkoutProducts(productRequest), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Payment endpoint: charges the user using a previously saved card (payment method).
     * This endpoint is used when the user has already saved a card (i.e., paymentMethodId is stored),
     * and wants to make a payment without re-entering card details.
     * Flow:
     * 1. The client sends a request with userId, cardId, amount, etc.
     * 2. The backend fetches the Stripe customer and the associated payment method.
     * 3. A PaymentIntent is created with 'off_session' and 'confirm' flags for immediate charge.
     * 4. Stripe processes the payment using the saved card, and returns the result.
     */


    @PostMapping("/charge")
    public ResponseEntity<PaymentResponse> charge(@RequestBody PaymentRequest request) {
        try {
            return new ResponseEntity<>(paymentService.chargeUser(request), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
