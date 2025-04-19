package com.example.stripepaymentdemo.service;

import com.example.stripepaymentdemo.dto.request.PaymentRequest;
import com.example.stripepaymentdemo.dto.request.ProductRequest;
import com.example.stripepaymentdemo.dto.response.PaymentResponse;
import com.example.stripepaymentdemo.dto.response.StripeResponse;

public interface PaymentService {
    StripeResponse checkoutProducts(ProductRequest productRequest);

    PaymentResponse chargeUser(PaymentRequest request);
}
