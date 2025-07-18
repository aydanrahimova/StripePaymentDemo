package com.example.stripepaymentdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//these responses are used for checkout stripe payment
@RestController
public class HomeController {
    @GetMapping("/success")
    public String success() {
        return "Success payment";
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "Payment canceled";
    }
}
