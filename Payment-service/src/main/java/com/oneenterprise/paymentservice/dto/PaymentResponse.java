package com.oneenterprise.paymentservice.dto;

public class PaymentResponse {

    private Long paymentId;

    private Long orderId;

    private String paymentStatus;

    private String message;

    public PaymentResponse() {
    }

    public PaymentResponse(
            Long paymentId,
            Long orderId,
            String paymentStatus,
            String message) {

        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
        this.message = message;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}