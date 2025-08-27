package com.ecommerce.controller;

import com.ecommerce.entity.Order;
import com.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Order Management", description = "APIs for managing orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Create order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created",
                    content = @Content(schema = @Schema(implementation = Order.class)))
    })
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(order));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/number/{orderNumber}")
    @Operation(summary = "Get order by order number")
    public ResponseEntity<Order> getOrderByNumber(@PathVariable String orderNumber) {
        return orderService.findByOrderNumber(orderNumber).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody Order order) {
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel order")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "List orders (paged)")
    public ResponseEntity<Page<Order>> listOrders(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "List orders by user (paged)")
    public ResponseEntity<Page<Order>> listOrdersByUser(@PathVariable Long userId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId, pageable));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "List orders by status (paged)")
    public ResponseEntity<Page<Order>> listOrdersByStatus(@PathVariable Order.OrderStatus status,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.getOrdersByStatus(status, pageable));
    }

    @GetMapping("/payment-status/{status}")
    @Operation(summary = "List orders by payment status (paged)")
    public ResponseEntity<Page<Order>> listOrdersByPaymentStatus(@PathVariable Order.PaymentStatus status,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.getOrdersByPaymentStatus(status, pageable));
    }

    @GetMapping("/date-range")
    @Operation(summary = "List orders by date range (paged)")
    public ResponseEntity<Page<Order>> listOrdersByDateRange(@RequestParam LocalDateTime start,
                                                             @RequestParam LocalDateTime end,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.getOrdersByDateRange(start, end, pageable));
    }

    @GetMapping("/amount-range")
    @Operation(summary = "List orders by amount range (paged)")
    public ResponseEntity<Page<Order>> listOrdersByAmountRange(@RequestParam BigDecimal min,
                                                               @RequestParam BigDecimal max,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.getOrdersByAmountRange(min, max, pageable));
    }

    @GetMapping("/shipping/{method}")
    @Operation(summary = "List orders by shipping method (paged)")
    public ResponseEntity<Page<Order>> listOrdersByShipping(@PathVariable Order.ShippingMethod method,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.getOrdersByShippingMethod(method, pageable));
    }

    @GetMapping("/tracking/search")
    @Operation(summary = "Search orders by tracking number")
    public ResponseEntity<List<Order>> searchByTracking(@RequestParam String q) {
        return ResponseEntity.ok(orderService.searchOrdersByTrackingNumber(q));
    }

    @GetMapping("/attention")
    @Operation(summary = "List orders needing attention (paged)")
    public ResponseEntity<Page<Order>> ordersNeedingAttention(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.getOrdersNeedingAttention(pageable));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update order status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestParam Order.OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @PatchMapping("/{id}/payment-status")
    @Operation(summary = "Update payment status")
    public ResponseEntity<Order> updatePaymentStatus(@PathVariable Long id, @RequestParam Order.PaymentStatus status) {
        return ResponseEntity.ok(orderService.updatePaymentStatus(id, status));
    }

    @PatchMapping("/{id}/tracking")
    @Operation(summary = "Update tracking number")
    public ResponseEntity<Order> updateTracking(@PathVariable Long id, @RequestParam String number) {
        return ResponseEntity.ok(orderService.updateTrackingNumber(id, number));
    }

    @PatchMapping("/{id}/estimated-delivery")
    @Operation(summary = "Update estimated delivery")
    public ResponseEntity<Order> updateEstimatedDelivery(@PathVariable Long id, @RequestParam LocalDateTime delivery) {
        return ResponseEntity.ok(orderService.updateEstimatedDelivery(id, delivery));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel order with reason")
    public ResponseEntity<Order> cancel(@PathVariable Long id, @RequestParam String reason) {
        return ResponseEntity.ok(orderService.cancelOrder(id, reason));
    }

    @PostMapping("/{id}/pay")
    @Operation(summary = "Process payment")
    public ResponseEntity<Order> pay(@PathVariable Long id, @RequestParam String method) {
        return ResponseEntity.ok(orderService.processPayment(id, method));
    }

    @PostMapping("/{id}/ship")
    @Operation(summary = "Ship order")
    public ResponseEntity<Order> ship(@PathVariable Long id,
                                      @RequestParam String tracking,
                                      @RequestParam Order.ShippingMethod method) {
        return ResponseEntity.ok(orderService.shipOrder(id, tracking, method));
    }

    @PostMapping("/{id}/deliver")
    @Operation(summary = "Deliver order")
    public ResponseEntity<Order> deliver(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.deliverOrder(id));
    }

    @PostMapping("/{id}/return")
    @Operation(summary = "Return order")
    public ResponseEntity<Order> returnOrder(@PathVariable Long id, @RequestParam String reason) {
        return ResponseEntity.ok(orderService.returnOrder(id, reason));
    }

    @PostMapping("/{id}/refund")
    @Operation(summary = "Refund order")
    public ResponseEntity<Order> refund(@PathVariable Long id, @RequestParam BigDecimal amount, @RequestParam String reason) {
        return ResponseEntity.ok(orderService.refundOrder(id, amount, reason));
    }

    @GetMapping("/{id}/total")
    @Operation(summary = "Calculate order total")
    public ResponseEntity<BigDecimal> total(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.calculateOrderTotal(id));
    }

    @GetMapping("/{id}/can-cancel")
    @Operation(summary = "Check if order can be cancelled")
    public ResponseEntity<Boolean> canCancel(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.canBeCancelled(id));
    }

    @GetMapping("/{id}/can-return")
    @Operation(summary = "Check if order can be returned")
    public ResponseEntity<Boolean> canReturn(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.canBeReturned(id));
    }
}
