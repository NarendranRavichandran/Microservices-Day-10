package com.oneenterprise.userservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${app.message}")
    private String message;

    @Value("${app.timeout}")
    private int timeout;

    @Value("${app.environment}")
    private String environment;

    @GetMapping("/message")
    public String getMessage() {
        return message;
    }

    @GetMapping("/timeout")
    public int getTimeout() {
        return timeout;
    }

    @GetMapping("/environment")
    public String getEnvironment() {
        return environment;
    }
}