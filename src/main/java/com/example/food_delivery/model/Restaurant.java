package com.example.food_delivery.model;

import java.time.LocalDateTime;

public class Restaurant {
    private Integer restaurantId;
    private String restaurantName;
    private String location;
    private String phone;
    private Double rating;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 构造函数
    public Restaurant() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.rating = 0.0;
    }

    // Getters and Setters
    // ... (生成所有属性的getter和setter方法)

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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
}
