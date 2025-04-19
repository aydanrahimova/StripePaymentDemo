package com.example.stripepaymentdemo.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * I’m not actually using this class in my real payment flow.
 * Just leaving it here as a reminder that sending raw card info like number, expiry, and CVC
 * through the backend is not safe and not recommended. Stripe doesn’t allow it for security.
 * In a proper setup, the frontend should send card details directly to Stripe (using Stripe.js),
 * and I only work with the paymentMethodId on the backend.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardRequest {
    Long userId;
    String cardNumber;
    Long expMonth;
    Long expYear;
    String cvc;
}
