package com.ecommerce.service;

import com.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Order Service interface
 * 
 * Follows Single Responsibility Principle:
 * - Only responsible for order business logic operations
 * 
 * Follows Interface Segregation Principle:
 * - Clients depend only on methods they use
 * 
 * Follows Dependency Inversion Principle:
 * - High-level modules depend on this abstraction
 */
public interface OrderService {

    /**
     * Create a new order
     * 
     * @param order the order to create
     * @return the created order
     */
    Order createOrder(Order order);

    /**
     * Find order by ID
     * 
     * @param id the order ID
     * @return Optional containing the order if found
     */
    Optional<Order> findById(Long id);

    /**
     * Find order by order number
     * 
     * @param orderNumber the order number
     * @return Optional containing the order if found
     */
    Optional<Order> findByOrderNumber(String orderNumber);

    /**
     * Update order information
     * 
     * @param id the order ID
     * @param order the updated order information
     * @return the updated order
     */
    Order updateOrder(Long id, Order order);

    /**
     * Delete order by ID
     * 
     * @param id the order ID
     */
    void deleteOrder(Long id);

    /**
     * Get all orders with pagination
     * 
     * @param pageable pagination information
     * @return Page of orders
     */
    Page<Order> getAllOrders(Pageable pageable);

    /**
     * Get orders by user ID
     * 
     * @param userId the user ID
     * @param pageable pagination information
     * @return Page of orders for the user
     */
    Page<Order> getOrdersByUserId(Long userId, Pageable pageable);

    /**
     * Get orders by status
     * 
     * @param status the order status
     * @param pageable pagination information
     * @return Page of orders with the specified status
     */
    Page<Order> getOrdersByStatus(Order.OrderStatus status, Pageable pageable);

    /**
     * Get orders by payment status
     * 
     * @param paymentStatus the payment status
     * @param pageable pagination information
     * @return Page of orders with the specified payment status
     */
    Page<Order> getOrdersByPaymentStatus(Order.PaymentStatus paymentStatus, Pageable pageable);

    /**
     * Get orders by date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @param pageable pagination information
     * @return Page of orders within the specified date range
     */
    Page<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Get orders by total amount range
     * 
     * @param minAmount the minimum amount
     * @param maxAmount the maximum amount
     * @param pageable pagination information
     * @return Page of orders within the specified amount range
     */
    Page<Order> getOrdersByAmountRange(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable);

    /**
     * Get orders by shipping method
     * 
     * @param shippingMethod the shipping method
     * @param pageable pagination information
     * @return Page of orders with the specified shipping method
     */
    Page<Order> getOrdersByShippingMethod(Order.ShippingMethod shippingMethod, Pageable pageable);

    /**
     * Search orders by tracking number
     * 
     * @param trackingNumber the tracking number
     * @return List of orders with the specified tracking number
     */
    List<Order> searchOrdersByTrackingNumber(String trackingNumber);

    /**
     * Get orders that need attention (e.g., pending payment, delayed shipping)
     * 
     * @param pageable pagination information
     * @return Page of orders that need attention
     */
    Page<Order> getOrdersNeedingAttention(Pageable pageable);

    /**
     * Update order status
     * 
     * @param id the order ID
     * @param status the new status
     * @return the updated order
     */
    Order updateOrderStatus(Long id, Order.OrderStatus status);

    /**
     * Update payment status
     * 
     * @param id the order ID
     * @param paymentStatus the new payment status
     * @return the updated order
     */
    Order updatePaymentStatus(Long id, Order.PaymentStatus paymentStatus);

    /**
     * Update tracking number
     * 
     * @param id the order ID
     * @param trackingNumber the new tracking number
     * @return the updated order
     */
    Order updateTrackingNumber(Long id, String trackingNumber);

    /**
     * Update estimated delivery date
     * 
     * @param id the order ID
     * @param estimatedDelivery the new estimated delivery date
     * @return the updated order
     */
    Order updateEstimatedDelivery(Long id, LocalDateTime estimatedDelivery);

    /**
     * Cancel order
     * 
     * @param id the order ID
     * @param reason the cancellation reason
     * @return the cancelled order
     */
    Order cancelOrder(Long id, String reason);

    /**
     * Process order payment
     * 
     * @param id the order ID
     * @param paymentMethod the payment method used
     * @return the updated order
     */
    Order processPayment(Long id, String paymentMethod);

    /**
     * Ship order
     * 
     * @param id the order ID
     * @param trackingNumber the tracking number
     * @param shippingMethod the shipping method used
     * @return the updated order
     */
    Order shipOrder(Long id, String trackingNumber, Order.ShippingMethod shippingMethod);

    /**
     * Deliver order
     * 
     * @param id the order ID
     * @return the delivered order
     */
    Order deliverOrder(Long id);

    /**
     * Return order
     * 
     * @param id the order ID
     * @param reason the return reason
     * @return the returned order
     */
    Order returnOrder(Long id, String reason);

    /**
     * Refund order
     * 
     * @param id the order ID
     * @param amount the refund amount
     * @param reason the refund reason
     * @return the refunded order
     */
    Order refundOrder(Long id, BigDecimal amount, String reason);

    /**
     * Calculate order total
     * 
     * @param id the order ID
     * @return the calculated total amount
     */
    BigDecimal calculateOrderTotal(Long id);

    /**
     * Check if order can be cancelled
     * 
     * @param id the order ID
     * @return true if order can be cancelled, false otherwise
     */
    boolean canBeCancelled(Long id);

    /**
     * Check if order can be returned
     * 
     * @param id the order ID
     * @return true if order can be returned, false otherwise
     */
    boolean canBeReturned(Long id);

    /**
     * Get order statistics
     * 
     * @param startDate the start date for statistics
     * @param endDate the end date for statistics
     * @return OrderStats containing various order statistics
     */
    OrderStats getOrderStatistics(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Get order count by status
     * 
     * @param status the order status
     * @return number of orders with the specified status
     */
    long countByStatus(Order.OrderStatus status);

    /**
     * Get order count by payment status
     * 
     * @param paymentStatus the payment status
     * @return number of orders with the specified payment status
     */
    long countByPaymentStatus(Order.PaymentStatus paymentStatus);

    /**
     * Get total revenue for a date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return total revenue for the specified date range
     */
    BigDecimal getTotalRevenue(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Get average order value for a date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return average order value for the specified date range
     */
    BigDecimal getAverageOrderValue(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * DTO for order statistics
     */
    class OrderStats {
        private long totalOrders;
        private long pendingOrders;
        private long processingOrders;
        private long shippedOrders;
        private long deliveredOrders;
        private long cancelledOrders;
        private long returnedOrders;
        private BigDecimal totalRevenue;
        private BigDecimal averageOrderValue;
        private long totalItemsSold;

        // Getters and Setters
        public long getTotalOrders() {
            return totalOrders;
        }

        public void setTotalOrders(long totalOrders) {
            this.totalOrders = totalOrders;
        }

        public long getPendingOrders() {
            return pendingOrders;
        }

        public void setPendingOrders(long pendingOrders) {
            this.pendingOrders = pendingOrders;
        }

        public long getProcessingOrders() {
            return processingOrders;
        }

        public void setProcessingOrders(long processingOrders) {
            this.processingOrders = processingOrders;
        }

        public long getShippedOrders() {
            return shippedOrders;
        }

        public void setShippedOrders(long shippedOrders) {
            this.shippedOrders = shippedOrders;
        }

        public long getDeliveredOrders() {
            return deliveredOrders;
        }

        public void setDeliveredOrders(long deliveredOrders) {
            this.deliveredOrders = deliveredOrders;
        }

        public long getCancelledOrders() {
            return cancelledOrders;
        }

        public void setCancelledOrders(long cancelledOrders) {
            this.cancelledOrders = cancelledOrders;
        }

        public long getReturnedOrders() {
            return returnedOrders;
        }

        public void setReturnedOrders(long returnedOrders) {
            this.returnedOrders = returnedOrders;
        }

        public BigDecimal getTotalRevenue() {
            return totalRevenue;
        }

        public void setTotalRevenue(BigDecimal totalRevenue) {
            this.totalRevenue = totalRevenue;
        }

        public BigDecimal getAverageOrderValue() {
            return averageOrderValue;
        }

        public void setAverageOrderValue(BigDecimal averageOrderValue) {
            this.averageOrderValue = averageOrderValue;
        }

        public long getTotalItemsSold() {
            return totalItemsSold;
        }

        public void setTotalItemsSold(long totalItemsSold) {
            this.totalItemsSold = totalItemsSold;
        }
    }
}
