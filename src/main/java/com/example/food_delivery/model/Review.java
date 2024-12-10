package com.example.food_delivery.model;

import java.time.LocalDateTime;

public class Review {
    private Integer reviewId;
    private Integer orderId;
    private Integer userId;
    private Double rating;
    private String comment;
    private LocalDateTime createTime;

    // 构造函数
    public Review() {
        this.createTime = LocalDateTime.now();
    }

    // Getters and Setters
    // ... (生成所有属性的getter和setter方法)

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}