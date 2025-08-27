package com.ecommerce.service.impl;

import com.ecommerce.entity.Order;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        if (order.getSubtotal() == null) {
            order.setSubtotal(BigDecimal.ZERO);
        }
        if (order.getTaxAmount() == null) {
            order.setTaxAmount(BigDecimal.ZERO);
        }
        if (order.getShippingCost() == null) {
            order.setShippingCost(BigDecimal.ZERO);
        }
        if (order.getDiscountAmount() == null) {
            order.setDiscountAmount(BigDecimal.ZERO);
        }
        order.calculateTotal();
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

        existing.setUser(order.getUser());
        existing.setShippingAddress(order.getShippingAddress());
        existing.setBillingAddress(order.getBillingAddress());
        existing.setSubtotal(order.getSubtotal());
        existing.setTaxAmount(order.getTaxAmount());
        existing.setShippingCost(order.getShippingCost());
        existing.setDiscountAmount(order.getDiscountAmount());
        existing.setStatus(order.getStatus());
        existing.setPaymentStatus(order.getPaymentStatus());
        existing.setShippingMethod(order.getShippingMethod());
        existing.setTrackingNumber(order.getTrackingNumber());
        existing.setEstimatedDelivery(order.getEstimatedDelivery());
        existing.setNotes(order.getNotes());

        existing.calculateTotal();
        return orderRepository.save(existing);
    }

    @Override
    public void deleteOrder(Long id) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        existing.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(existing);
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> getOrdersByUserId(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable);
    }

    @Override
    public Page<Order> getOrdersByStatus(Order.OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable);
    }

    @Override
    public Page<Order> getOrdersByPaymentStatus(Order.PaymentStatus paymentStatus, Pageable pageable) {
        return orderRepository.findByPaymentStatus(paymentStatus, pageable);
    }

    @Override
    public Page<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return orderRepository.findByDateRange(startDate, endDate, pageable);
    }

    @Override
    public Page<Order> getOrdersByAmountRange(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable) {
        return orderRepository.findByAmountRange(minAmount, maxAmount, pageable);
    }

    @Override
    public Page<Order> getOrdersByShippingMethod(Order.ShippingMethod shippingMethod, Pageable pageable) {
        return orderRepository.findByShippingMethod(shippingMethod, pageable);
    }

    @Override
    public List<Order> searchOrdersByTrackingNumber(String trackingNumber) {
        return orderRepository.findByTrackingNumberContainingIgnoreCase(trackingNumber);
    }

    @Override
    public Page<Order> getOrdersNeedingAttention(Pageable pageable) {
        return orderRepository.findOrdersNeedingAttention(pageable);
    }

    @Override
    public Order updateOrderStatus(Long id, Order.OrderStatus status) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        existing.setStatus(status);
        return orderRepository.save(existing);
    }

    @Override
    public Order updatePaymentStatus(Long id, Order.PaymentStatus paymentStatus) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        existing.setPaymentStatus(paymentStatus);
        return orderRepository.save(existing);
    }

    @Override
    public Order updateTrackingNumber(Long id, String trackingNumber) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        existing.setTrackingNumber(trackingNumber);
        return orderRepository.save(existing);
    }

    @Override
    public Order updateEstimatedDelivery(Long id, LocalDateTime estimatedDelivery) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        existing.setEstimatedDelivery(estimatedDelivery);
        return orderRepository.save(existing);
    }

    @Override
    public Order cancelOrder(Long id, String reason) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        if (!existing.canBeCancelled()) {
            throw new IllegalStateException("Order cannot be cancelled in its current status");
        }
        existing.setStatus(Order.OrderStatus.CANCELLED);
        existing.setNotes(reason);
        return orderRepository.save(existing);
    }

    @Override
    public Order processPayment(Long id, String paymentMethod) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        existing.setPaymentStatus(Order.PaymentStatus.PAID);
        return orderRepository.save(existing);
    }

    @Override
    public Order shipOrder(Long id, String trackingNumber, Order.ShippingMethod shippingMethod) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        existing.setStatus(Order.OrderStatus.SHIPPED);
        existing.setTrackingNumber(trackingNumber);
        existing.setShippingMethod(shippingMethod);
        return orderRepository.save(existing);
    }

    @Override
    public Order deliverOrder(Long id) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        existing.setStatus(Order.OrderStatus.DELIVERED);
        return orderRepository.save(existing);
    }

    @Override
    public Order returnOrder(Long id, String reason) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        existing.setStatus(Order.OrderStatus.REFUNDED);
        existing.setNotes(reason);
        return orderRepository.save(existing);
    }

    @Override
    public Order refundOrder(Long id, BigDecimal amount, String reason) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        existing.setPaymentStatus(Order.PaymentStatus.REFUNDED);
        existing.setNotes(reason);
        return orderRepository.save(existing);
    }

    @Override
    public BigDecimal calculateOrderTotal(Long id) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        existing.calculateTotal();
        orderRepository.save(existing);
        return existing.getTotalAmount();
    }

    @Override
    public boolean canBeCancelled(Long id) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        return existing.canBeCancelled();
    }

    @Override
    public boolean canBeReturned(Long id) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        return existing.isDelivered();
    }

    @Override
    public OrderStats getOrderStatistics(LocalDateTime startDate, LocalDateTime endDate) {
        OrderStats stats = new OrderStats();
        stats.setTotalOrders(orderRepository.count());
        stats.setPendingOrders(orderRepository.countByStatus(Order.OrderStatus.PENDING));
        stats.setProcessingOrders(orderRepository.countByStatus(Order.OrderStatus.PROCESSING));
        stats.setShippedOrders(orderRepository.countByStatus(Order.OrderStatus.SHIPPED));
        stats.setDeliveredOrders(orderRepository.countByStatus(Order.OrderStatus.DELIVERED));
        stats.setCancelledOrders(orderRepository.countByStatus(Order.OrderStatus.CANCELLED));
        stats.setReturnedOrders(orderRepository.countByStatus(Order.OrderStatus.REFUNDED));
        stats.setTotalRevenue(orderRepository.sumRevenueBetween(startDate, endDate));
        stats.setAverageOrderValue(orderRepository.avgOrderValueBetween(startDate, endDate));
        // totalItemsSold would come from OrderItem aggregation; placeholder 0 for now
        stats.setTotalItemsSold(0);
        return stats;
    }

    @Override
    public long countByStatus(Order.OrderStatus status) {
        return orderRepository.countByStatus(status);
    }

    @Override
    public long countByPaymentStatus(Order.PaymentStatus paymentStatus) {
        return orderRepository.countByPaymentStatus(paymentStatus);
    }

    @Override
    public BigDecimal getTotalRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.sumRevenueBetween(startDate, endDate);
    }

    @Override
    public BigDecimal getAverageOrderValue(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.avgOrderValueBetween(startDate, endDate);
    }
}
