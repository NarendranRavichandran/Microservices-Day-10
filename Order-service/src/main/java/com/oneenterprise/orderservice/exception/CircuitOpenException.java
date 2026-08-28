package com.oneenterprise.orderservice.exception;

public class CircuitOpenException
        extends RuntimeException {

    public CircuitOpenException(String message) {
        super(message);
    }
}