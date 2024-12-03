package com.example.food_delivery.model;

import java.time.LocalDateTime;

public class DeliveryStats {
    private Integer statsId;
    private Integer deliveryPersonId;
    private Integer totalDeliveries;
    private Integer successfulDeliveries;
    private Integer failedDeliveries;
    private Double avgDeliveryTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 构造函数
    public DeliveryStats() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.totalDeliveries = 0;
        this.successfulDeliveries = 0;
        this.failedDeliveries = 0;
        this.avgDeliveryTime = 0.0;
    }

    // Getters and Setters
    // ... (生成所有属性的getter和setter方法)

    public Integer getStatsId() {
        return statsId;
    }

    public void setStatsId(Integer statsId) {
        this.statsId = statsId;
    }

    public Integer getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(Integer deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public Integer getTotalDeliveries() {
        return totalDeliveries;
    }

    public void setTotalDeliveries(Integer totalDeliveries) {
        this.totalDeliveries = totalDeliveries;
    }

    public Integer getSuccessfulDeliveries() {
        return successfulDeliveries;
    }

    public void setSuccessfulDeliveries(Integer successfulDeliveries) {
        this.successfulDeliveries = successfulDeliveries;
    }

    public Integer getFailedDeliveries() {
        return failedDeliveries;
    }

    public void setFailedDeliveries(Integer failedDeliveries) {
        this.failedDeliveries = failedDeliveries;
    }

    public Double getAvgDeliveryTime() {
        return avgDeliveryTime;
    }

    public void setAvgDeliveryTime(Double avgDeliveryTime) {
        this.avgDeliveryTime = avgDeliveryTime;
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