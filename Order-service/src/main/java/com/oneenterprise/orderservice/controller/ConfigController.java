package com.oneenterprise.orderservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${app.message}")
    private String message;

    @Value("${app.environment}")
    private String environment;

    @Value("${app.timeout}")
    private int timeout;

    @GetMapping("/message")
    public String message() {

        return message;
    }

    @GetMapping("/environment")
    public String environment() {

        return environment;
    }

    @GetMapping("/timeout")
    public int timeout() {

        return timeout;
    }
}