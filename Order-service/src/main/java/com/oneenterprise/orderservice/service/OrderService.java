package com.oneenterprise.orderservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oneenterprise.orderservice.client.PaymentClient;
import com.oneenterprise.orderservice.client.UserClient;
import com.oneenterprise.orderservice.dto.CreateOrderRequest;
import com.oneenterprise.orderservice.dto.OrderItemRequest;
import com.oneenterprise.orderservice.dto.OrderItemResponse;
import com.oneenterprise.orderservice.dto.OrderResponse;
import com.oneenterprise.orderservice.dto.PaymentResponse;
import com.oneenterprise.orderservice.dto.UserResponse;
import com.oneenterprise.orderservice.entity.Order;
import com.oneenterprise.orderservice.entity.OrderItem;
import com.oneenterprise.orderservice.exception.DuplicateOrderException;
import com.oneenterprise.orderservice.exception.InvalidOrderException;
import com.oneenterprise.orderservice.exception.OrderNotFoundException;
import com.oneenterprise.orderservice.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserClient userClient;

    private final PaymentClient paymentClient;

    public OrderService(
            OrderRepository orderRepository,
            UserClient userClient,
            PaymentClient paymentClient) {

        this.orderRepository =
                orderRepository;

        this.userClient =
                userClient;

        this.paymentClient =
                paymentClient;
    }

    @Transactional
    public OrderResponse createOrder(
            CreateOrderRequest request) {

        validateBusinessRules(request);

        UserResponse user =
                userClient.getUser(
                        request.getUserId()
                );

        if (user == null ||
                user.getName() == null ||
                user.getName().isBlank()) {

            throw new InvalidOrderException(
                    "User could not be verified"
            );
        }

        boolean duplicate =
                orderRepository
                        .existsByUserIdAndStatus(
                                request.getUserId(),
                                "CONFIRMED"
                        );

        if (duplicate) {

            throw new DuplicateOrderException(
                    "A confirmed order already exists for user "
                            + request.getUserId()
            );
        }

        Order order =
                new Order();

        order.setUserId(
                request.getUserId()
        );

        order.setStatus(
                "CREATED"
        );

        for (
                OrderItemRequest itemRequest :
                request.getItems()
        ) {

            OrderItem item =
                    new OrderItem();

            item.setProductId(
                    itemRequest.getProductId()
            );

            item.setQuantity(
                    itemRequest.getQuantity()
            );

            order.addItem(item);
        }

        Order savedOrder =
                orderRepository.save(order);

        savedOrder.setStatus(
                "CONFIRMED"
        );

        savedOrder =
                orderRepository.save(
                        savedOrder
                );

        PaymentResponse payment =
                paymentClient.processPayment(
                        savedOrder.getId()
                );

        List<OrderItemResponse> itemResponses =
                new ArrayList<>();

        for (
                OrderItem item :
                savedOrder.getItems()
        ) {

            itemResponses.add(
                    new OrderItemResponse(
                            item.getProductId(),
                            item.getQuantity()
                    )
            );
        }

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getStatus(),
                user,
                payment,
                itemResponses
        );
    }

    public OrderResponse getOrderById(
            Long orderId) {

        Order order =
                orderRepository
                        .findById(orderId)
                        .orElseThrow(
                                () ->
                                        new OrderNotFoundException(
                                                orderId
                                        )
                        );

        UserResponse user =
                userClient.getUser(
                        order.getUserId()
                );

        PaymentResponse payment =
                paymentClient.processPayment(
                        order.getId()
                );

        List<OrderItemResponse> itemResponses =
                new ArrayList<>();

        for (
                OrderItem item :
                order.getItems()
        ) {

            itemResponses.add(
                    new OrderItemResponse(
                            item.getProductId(),
                            item.getQuantity()
                    )
            );
        }

        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getStatus(),
                user,
                payment,
                itemResponses
        );
    }

    private void validateBusinessRules(
            CreateOrderRequest request) {

        if (request.getUserId() == null ||
                request.getUserId() <= 0) {

            throw new InvalidOrderException(
                    "User ID must be greater than 0"
            );
        }

        if (request.getItems() == null ||
                request.getItems().isEmpty()) {

            throw new InvalidOrderException(
                    "Order must contain at least one item"
            );
        }

        for (
                OrderItemRequest item :
                request.getItems()
        ) {

            if (item.getQuantity() == null ||
                    item.getQuantity() <= 0) {

                throw new InvalidOrderException(
                        "Item quantity must be greater than 0"
                );
            }
        }
    }

    public String getUserCircuitState() {

        return userClient
                .getCircuitState();
    }
}