package com.example.food_delivery.model;

import java.time.LocalDateTime;

public class Delivery {
    private Integer deliveryId;
    private Integer orderId;
    private Integer deliveryPersonId;
    private DeliveryStatus deliveryStatus;
    private LocalDateTime deliveryTime;

    public enum DeliveryStatus {
        PENDING("待接单"),
        IN_PROGRESS("配送中"),
        DELIVERED("已送达"),
        FAILED("配送失败");

        private final String value;

        DeliveryStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // 构造函数
    public Delivery() {
        this.deliveryStatus = DeliveryStatus.PENDING;
        this.deliveryTime = LocalDateTime.now();
    }

    // Getters and Setters
    // ... (生成所有属性的getter和setter方法)

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(Integer deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
