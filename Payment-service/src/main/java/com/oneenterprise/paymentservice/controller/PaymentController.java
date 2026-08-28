package com.oneenterprise.paymentservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oneenterprise.paymentservice.dto.PaymentResponse;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${payment.simulate-delay-ms:0}")
    private long configuredDelay;

    @Value("${payment.simulate-failure:false}")
    private boolean configuredFailure;

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> processPayment(
            @PathVariable Long orderId,
            @RequestParam(
                    name = "delayMs",
                    required = false
            ) Long delayMs,
            @RequestParam(
                    name = "fail",
                    required = false
            ) Boolean fail) {

        long actualDelay =
                delayMs != null
                        ? delayMs
                        : configuredDelay;

        boolean actualFailure =
                fail != null
                        ? fail
                        : configuredFailure;

        if (actualDelay > 0) {

            try {

                Thread.sleep(actualDelay);

            } catch (InterruptedException exception) {

                Thread.currentThread().interrupt();

                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build();
            }
        }

        if (actualFailure) {

            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(
                            new PaymentResponse(
                                    null,
                                    orderId,
                                    "FAILED",
                                    "Payment service is temporarily unavailable"
                            )
                    );
        }

        PaymentResponse response =
                new PaymentResponse(
                        5000L + orderId,
                        orderId,
                        "SUCCESS",
                        "Payment processed successfully"
                );

        return ResponseEntity.ok(response);
    }
}