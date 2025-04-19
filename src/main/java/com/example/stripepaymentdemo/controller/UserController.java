package com.example.stripepaymentdemo.controller;

import com.example.stripepaymentdemo.dto.request.UserRequest;
import com.example.stripepaymentdemo.dto.response.UserResponse;
import com.example.stripepaymentdemo.service.UserService;
import com.stripe.exception.StripeException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request) throws StripeException {
        return userService.createUser(request);
    }

}
