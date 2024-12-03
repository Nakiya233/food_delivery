package com.example.food_delivery.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T, ID> {
    // 保存实体
    T save(T entity);
    
    // 根据ID查找
    Optional<T> findById(ID id);
    
    // 查找所有
    List<T> findAll();
    
    // 更新实体
    T update(T entity);
    
    // 删除实体
    void delete(ID id);
    
    // 检查是否存在
    boolean exists(ID id);
} 