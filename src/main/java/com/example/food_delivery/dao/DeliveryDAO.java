package com.example.food_delivery.dao;

import com.example.food_delivery.model.Delivery;
import com.example.food_delivery.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeliveryDAO implements BaseDAO<Delivery, Integer> {
    
    @Override
    public Delivery save(Delivery delivery) {
        String sql = "INSERT INTO deliveries (order_id, delivery_person, delivery_status, delivery_time) " +
                    "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, delivery.getOrderId());
            pstmt.setInt(2, delivery.getDeliveryPersonId());
            pstmt.setString(3, delivery.getDeliveryStatus().name());
            pstmt.setTimestamp(4, Timestamp.valueOf(delivery.getDeliveryTime()));
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating delivery failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    delivery.setDeliveryId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating delivery failed, no ID obtained.");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return delivery;
    }
    
    @Override
    public Optional<Delivery> findById(Integer id) {
        String sql = "SELECT * FROM deliveries WHERE delivery_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Delivery delivery = new Delivery();
                delivery.setDeliveryId(rs.getInt("delivery_id"));
                delivery.setOrderId(rs.getInt("order_id"));
                delivery.setDeliveryPersonId(rs.getInt("delivery_person"));
                delivery.setDeliveryStatus(Delivery.DeliveryStatus.valueOf(rs.getString("delivery_status")));
                delivery.setDeliveryTime(rs.getTimestamp("delivery_time").toLocalDateTime());
                
                return Optional.of(delivery);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Delivery> findAll() {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT * FROM deliveries";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Delivery delivery = new Delivery();
                delivery.setDeliveryId(rs.getInt("delivery_id"));
                delivery.setOrderId(rs.getInt("order_id"));
                delivery.setDeliveryPersonId(rs.getInt("delivery_person"));
                delivery.setDeliveryStatus(Delivery.DeliveryStatus.valueOf(rs.getString("delivery_status")));
                delivery.setDeliveryTime(rs.getTimestamp("delivery_time").toLocalDateTime());
                
                deliveries.add(delivery);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return deliveries;
    }
    
    @Override
    public Delivery update(Delivery delivery) {
        String sql = "UPDATE deliveries SET order_id = ?, delivery_person = ?, delivery_status = ?, " +
                    "delivery_time = ? WHERE delivery_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, delivery.getOrderId());
            pstmt.setInt(2, delivery.getDeliveryPersonId());
            pstmt.setString(3, delivery.getDeliveryStatus().name());
            pstmt.setTimestamp(4, Timestamp.valueOf(delivery.getDeliveryTime()));
            pstmt.setInt(5, delivery.getDeliveryId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return delivery;
    }
    
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM deliveries WHERE delivery_id = ?";
        
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
        String sql = "SELECT COUNT(*) FROM deliveries WHERE delivery_id = ?";
        
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
    
    // 额外的配送相关方法
    public List<Delivery> findByDeliveryPerson(Integer deliveryPersonId) {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT * FROM deliveries WHERE delivery_person = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, deliveryPersonId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Delivery delivery = new Delivery();
                delivery.setDeliveryId(rs.getInt("delivery_id"));
                delivery.setOrderId(rs.getInt("order_id"));
                delivery.setDeliveryPersonId(rs.getInt("delivery_person"));
                delivery.setDeliveryStatus(Delivery.DeliveryStatus.valueOf(rs.getString("delivery_status")));
                delivery.setDeliveryTime(rs.getTimestamp("delivery_time").toLocalDateTime());
                
                deliveries.add(delivery);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return deliveries;
    }
} 