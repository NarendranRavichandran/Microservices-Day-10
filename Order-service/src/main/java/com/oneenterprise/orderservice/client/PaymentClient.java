package com.oneenterprise.orderservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.oneenterprise.orderservice.dto.PaymentResponse;

@Component
public class PaymentClient {

    private final RestClient paymentServiceRestClient;

    public PaymentClient(
            RestClient paymentServiceRestClient) {

        this.paymentServiceRestClient =
                paymentServiceRestClient;
    }

    public PaymentResponse processPayment(
            Long orderId) {

        return paymentServiceRestClient
                .get()
                .uri(
                        "/api/payments/{orderId}",
                        orderId
                )
                .retrieve()
                .body(PaymentResponse.class);
    }

    public String getCircuitState() {

        return "CLOSED";
    }
}