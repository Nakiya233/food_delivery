package com.example.food_delivery.dao;

import com.example.food_delivery.model.Order;
import com.example.food_delivery.model.OrderItem;
import com.example.food_delivery.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAO implements BaseDAO<Order, Integer> {
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();
    
    @Override
    public Order save(Order order) {
        String sql = "INSERT INTO orders (user_id, restaurant_id, total_price, order_status, " +
                    "payment_status, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getRestaurantId());
            pstmt.setBigDecimal(3, order.getTotalPrice());
            pstmt.setString(4, order.getOrderStatus().name());
            pstmt.setString(5, order.getPaymentStatus().name());
            pstmt.setTimestamp(6, Timestamp.valueOf(order.getCreateTime()));
            pstmt.setTimestamp(7, Timestamp.valueOf(order.getUpdateTime()));
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setOrderId(generatedKeys.getInt(1));
                    
                    // 保存订单项
                    for (OrderItem item : order.getOrderItems()) {
                        item.setOrderId(order.getOrderId());
                        orderItemDAO.save(item);
                    }
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return order;
    }
    
    @Override
    public Optional<Order> findById(Integer id) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setRestaurantId(rs.getInt("restaurant_id"));
                order.setTotalPrice(rs.getBigDecimal("total_price"));
                order.setOrderStatus(Order.OrderStatus.valueOf(rs.getString("order_status")));
                order.setPaymentStatus(Order.PaymentStatus.valueOf(rs.getString("payment_status")));
                order.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                order.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                // 获取订单项
                List<OrderItem> orderItems = orderItemDAO.findByOrderId(order.getOrderId());
                order.setOrderItems(orderItems);
                
                return Optional.of(order);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setRestaurantId(rs.getInt("restaurant_id"));
                order.setTotalPrice(rs.getBigDecimal("total_price"));
                order.setOrderStatus(convertToOrderStatus(rs.getString("order_status")));
                order.setPaymentStatus(convertToPaymentStatus(rs.getString("payment_status")));
                order.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                order.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                // 获取订单项
                List<OrderItem> orderItems = orderItemDAO.findByOrderId(order.getOrderId());
                order.setOrderItems(orderItems);
                
                orders.add(order);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orders;
    }
    
    @Override
    public Order update(Order order) {
        String sql = "UPDATE orders SET user_id = ?, restaurant_id = ?, total_price = ?, " +
                    "order_status = ?, payment_status = ?, update_time = ? WHERE order_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getRestaurantId());
            pstmt.setBigDecimal(3, order.getTotalPrice());
            pstmt.setString(4, order.getOrderStatus().name());
            pstmt.setString(5, order.getPaymentStatus().name());
            pstmt.setTimestamp(6, Timestamp.valueOf(order.getUpdateTime()));
            pstmt.setInt(7, order.getOrderId());
            
            pstmt.executeUpdate();
            
            // 更新订单项
            for (OrderItem item : order.getOrderItems()) {
                orderItemDAO.update(item);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return order;
    }
    
    @Override
    public void delete(Integer id) {
        // 首先删除关联的订单项
        orderItemDAO.deleteByOrderId(id);
        
        // 然后删除订单
        String sql = "DELETE FROM orders WHERE order_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean exists(Integer id) {
        String sql = "SELECT COUNT(*) FROM orders WHERE order_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // 额外的订单相关方法
    public List<Order> findByUserId(Integer userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setRestaurantId(rs.getInt("restaurant_id"));
                order.setTotalPrice(rs.getBigDecimal("total_price"));
                order.setOrderStatus(Order.OrderStatus.valueOf(rs.getString("order_status")));
                order.setPaymentStatus(Order.PaymentStatus.valueOf(rs.getString("payment_status")));
                order.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                order.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                // 获取订单项
                List<OrderItem> orderItems = orderItemDAO.findByOrderId(order.getOrderId());
                order.setOrderItems(orderItems);
                
                orders.add(order);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orders;
    }
    
    private Order.OrderStatus convertToOrderStatus(String status) {
        for (Order.OrderStatus orderStatus : Order.OrderStatus.values()) {
            if (orderStatus.getValue().equals(status)) {
                return orderStatus;
            }
        }
        return Order.OrderStatus.PENDING; // 默认值
    }
    
    private Order.PaymentStatus convertToPaymentStatus(String status) {
        for (Order.PaymentStatus paymentStatus : Order.PaymentStatus.values()) {
            if (paymentStatus.getValue().equals(status)) {
                return paymentStatus;
            }
        }
        return Order.PaymentStatus.PENDING; // 默认值
    }
} 