package com.example.stripepaymentdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
