package com.oneenterprise.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient.Builder restClientBuilder() {

        return RestClient.builder();
    }

    @Bean
    public RestClient userServiceRestClient(
            RestClient.Builder builder) {

        return builder
                .baseUrl("http://localhost:8081")
                .build();
    }

    @Bean
    public RestClient paymentServiceRestClient(
            RestClient.Builder builder) {

        return builder
                .baseUrl("http://localhost:8083")
                .build();
    }
}