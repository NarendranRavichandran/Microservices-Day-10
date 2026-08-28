package com.oneenterprise.orderservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.oneenterprise.orderservice.dto.UserResponse;

@Component
public class UserClient {

    private static final Logger logger =
            LoggerFactory.getLogger(UserClient.class);

    private final RestClient userServiceRestClient;

    private final CircuitBreakerFactory<?, ?> cbFactory;

    public UserClient(
            RestClient userServiceRestClient,
            CircuitBreakerFactory<?, ?> cbFactory) {

        this.userServiceRestClient =
                userServiceRestClient;

        this.cbFactory = cbFactory;
    }

    public UserResponse getUser(Long userId) {

        logger.info(
                "Calling User Service for userId={}",
                userId
        );

        return cbFactory
                .create("userService")
                .run(
                        () -> {

                            logger.info(
                                    "Sending request to User Service: /users/{}",
                                    userId
                            );

                            return userServiceRestClient
                                    .get()
                                    .uri(
                                            "/users/{id}",
                                            userId
                                    )
                                    .retrieve()
                                    .body(UserResponse.class);
                        },

                        throwable -> {

                            logger.error(
                                    "User Service failed for userId={}. Using fallback.",
                                    userId,
                                    throwable
                            );

                            return userServiceFallback(
                                    userId
                            );
                        }
                );
    }

    private UserResponse userServiceFallback(
            Long userId) {

        logger.warn(
                "Executing User Service fallback for userId={}",
                userId
        );

        return new UserResponse(
                userId,
                "User temporarily unavailable",
                null
        );
    }

    public String getCircuitState() {
        return "CLOSED";
    }
}