package com.oneenterprise.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneenterprise.orderservice.entity.Order;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    boolean existsByUserIdAndStatus(
            Long userId,
            String status
    );
}