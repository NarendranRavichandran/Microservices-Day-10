package com.oneenterprise.orderservice.dto;

import java.util.List;

public class OrderResponse {

    private Long orderId;

    private Long userId;

    private String orderStatus;

    private UserResponse user;

    private PaymentResponse payment;

    private List<OrderItemResponse> items;

    public OrderResponse() {
    }

    public OrderResponse(
            Long orderId,
            Long userId,
            String orderStatus,
            UserResponse user,
            PaymentResponse payment,
            List<OrderItemResponse> items) {

        this.orderId = orderId;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.user = user;
        this.payment = payment;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public PaymentResponse getPayment() {
        return payment;
    }

    public void setPayment(PaymentResponse payment) {
        this.payment = payment;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(
            List<OrderItemResponse> items) {

        this.items = items;
    }
}