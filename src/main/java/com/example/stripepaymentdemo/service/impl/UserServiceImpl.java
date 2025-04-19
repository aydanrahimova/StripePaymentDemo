package com.example.stripepaymentdemo.service.impl;

import com.example.stripepaymentdemo.dto.request.UserRequest;
import com.example.stripepaymentdemo.dto.response.UserResponse;
import com.example.stripepaymentdemo.entity.User;
import com.example.stripepaymentdemo.mapper.UserMapper;
import com.example.stripepaymentdemo.repository.UserRepository;
import com.example.stripepaymentdemo.service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserMapper userMapper;
    UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest request) throws StripeException {
        log.info("Operation of creating new user started...");
        User user = userMapper.toEntity(request);
        //A Customer in Stripe is a virtual representation of your user inside Stripe’s system.
        //It stores all the user-related payment stuff in one place, such as saved payment methods (cards, wallets, etc.),payment history
        Customer customer = Customer.create(
                CustomerCreateParams.builder().setEmail(request.getEmail()).build()
        );
        user.setStripeCustomerId(customer.getId());
        userRepository.save(user);
        UserResponse userResponse = userMapper.toDto(user);
        log.info("New user successfully saved");
        return userResponse;
    }

}
