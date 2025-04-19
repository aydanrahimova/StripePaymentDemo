package com.example.stripepaymentdemo.service;

import com.example.stripepaymentdemo.dto.request.UserRequest;
import com.example.stripepaymentdemo.dto.response.UserResponse;
import com.stripe.exception.StripeException;

public interface UserService {
    UserResponse createUser(UserRequest request) throws StripeException;
}
