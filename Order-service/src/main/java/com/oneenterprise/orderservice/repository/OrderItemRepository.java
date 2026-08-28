package com.oneenterprise.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneenterprise.orderservice.entity.OrderItem;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {
}