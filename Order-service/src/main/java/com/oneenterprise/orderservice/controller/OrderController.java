package com.oneenterprise.orderservice.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.oneenterprise.orderservice.dto.CreateOrderRequest;
import com.oneenterprise.orderservice.dto.OrderResponse;
import com.oneenterprise.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(
            OrderService orderService) {

        this.orderService =
                orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse>
    createOrder(
            @Valid
            @RequestBody
            CreateOrderRequest request) {

        OrderResponse response =
                orderService.createOrder(
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse>
    getOrder(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderService.getOrderById(
                        orderId
                )
        );
    }

    @GetMapping("/circuit-state")
    public String circuitState() {

        return orderService
                .getUserCircuitState();
    }
}