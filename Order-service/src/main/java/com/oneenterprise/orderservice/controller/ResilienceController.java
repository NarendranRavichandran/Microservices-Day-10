package com.oneenterprise.orderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oneenterprise.orderservice.client.UserClient;

@RestController
public class ResilienceController {

    private final UserClient userClient;

    public ResilienceController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/resilience/user-service")
    public String getUserServiceCircuitState() {

        return userClient.getCircuitState();
    }
}