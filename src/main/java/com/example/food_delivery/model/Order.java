package com.example.food_delivery.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Order {
    private Integer orderId;
    private Integer userId;
    private Integer restaurantId;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<OrderItem> orderItems;

    public enum OrderStatus {
        PENDING("待支付"),
        PAID("已支付"),
        COMPLETED("已完成"),
        CANCELLED("已取消");

        private final String value;

        OrderStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum PaymentStatus {
        PENDING("待支付"),
        PAID("已支付"),
        FAILED("支付失败");

        private final String value;

        PaymentStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // 构造函数
    public Order() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.orderItems = new ArrayList<>();
        this.orderStatus = OrderStatus.PENDING;
        this.paymentStatus = PaymentStatus.PENDING;
        this.totalPrice = BigDecimal.ZERO;
    }

    // Getters and Setters
    // ... (生成所有属性的getter和setter方法)

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}