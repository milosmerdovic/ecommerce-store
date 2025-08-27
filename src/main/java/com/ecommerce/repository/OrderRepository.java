package com.ecommerce.repository;

import com.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    Page<Order> findByUserId(Long userId, Pageable pageable);

    Page<Order> findByStatus(Order.OrderStatus status, Pageable pageable);

    Page<Order> findByPaymentStatus(Order.PaymentStatus paymentStatus, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :start AND :end")
    Page<Order> findByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.totalAmount BETWEEN :minAmount AND :maxAmount")
    Page<Order> findByAmountRange(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable);

    Page<Order> findByShippingMethod(Order.ShippingMethod shippingMethod, Pageable pageable);

    List<Order> findByTrackingNumberContainingIgnoreCase(String trackingNumber);

    @Query("SELECT o FROM Order o WHERE o.status IN (com.ecommerce.entity.Order$OrderStatus.PENDING, com.ecommerce.entity.Order$OrderStatus.PROCESSING) OR o.paymentStatus = com.ecommerce.entity.Order$PaymentStatus.PENDING")
    Page<Order> findOrdersNeedingAttention(Pageable pageable);

    long countByStatus(Order.OrderStatus status);

    long countByPaymentStatus(Order.PaymentStatus paymentStatus);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.createdAt BETWEEN :start AND :end")
    BigDecimal sumRevenueBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COALESCE(AVG(o.totalAmount), 0) FROM Order o WHERE o.createdAt BETWEEN :start AND :end")
    BigDecimal avgOrderValueBetween(LocalDateTime start, LocalDateTime end);
}
