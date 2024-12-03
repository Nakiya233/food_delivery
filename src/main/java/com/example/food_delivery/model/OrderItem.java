package com.example.food_delivery.model;

import java.math.BigDecimal;

public class OrderItem {
    private Integer orderItemId;
    private Integer orderId;
    private Integer menuId;
    private Integer quantity;
    private BigDecimal price;

    // 构造函数
    public OrderItem() {
        this.quantity = 1;
        this.price = BigDecimal.ZERO;
    }

    // Getters and Setters
    // ... (生成所有属性的getter和setter方法)

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
