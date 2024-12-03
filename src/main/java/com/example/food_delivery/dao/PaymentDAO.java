package com.example.food_delivery.dao;

import com.example.food_delivery.model.Payment;
import com.example.food_delivery.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentDAO implements BaseDAO<Payment, Integer> {
    
    @Override
    public Payment save(Payment payment) {
        String sql = "INSERT INTO payments (order_id, payment_method, payment_amount, payment_status, payment_time) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, payment.getOrderId());
            pstmt.setString(2, payment.getPaymentMethod().name());
            pstmt.setBigDecimal(3, payment.getPaymentAmount());
            pstmt.setString(4, payment.getPaymentStatus().name());
            pstmt.setTimestamp(5, Timestamp.valueOf(payment.getPaymentTime()));
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating payment failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payment.setPaymentId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating payment failed, no ID obtained.");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return payment;
    }
    
    @Override
    public Optional<Payment> findById(Integer id) {
        String sql = "SELECT * FROM payments WHERE payment_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setOrderId(rs.getInt("order_id"));
                payment.setPaymentMethod(Payment.PaymentMethod.valueOf(rs.getString("payment_method")));
                payment.setPaymentAmount(rs.getBigDecimal("payment_amount"));
                payment.setPaymentStatus(Payment.PaymentStatus.valueOf(rs.getString("payment_status")));
                payment.setPaymentTime(rs.getTimestamp("payment_time").toLocalDateTime());
                
                return Optional.of(payment);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Payment> findAll() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setOrderId(rs.getInt("order_id"));
                payment.setPaymentMethod(Payment.PaymentMethod.valueOf(rs.getString("payment_method")));
                payment.setPaymentAmount(rs.getBigDecimal("payment_amount"));
                payment.setPaymentStatus(Payment.PaymentStatus.valueOf(rs.getString("payment_status")));
                payment.setPaymentTime(rs.getTimestamp("payment_time").toLocalDateTime());
                
                payments.add(payment);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return payments;
    }
    
    @Override
    public Payment update(Payment payment) {
        String sql = "UPDATE payments SET order_id = ?, payment_method = ?, payment_amount = ?, " +
                    "payment_status = ?, payment_time = ? WHERE payment_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, payment.getOrderId());
            pstmt.setString(2, payment.getPaymentMethod().name());
            pstmt.setBigDecimal(3, payment.getPaymentAmount());
            pstmt.setString(4, payment.getPaymentStatus().name());
            pstmt.setTimestamp(5, Timestamp.valueOf(payment.getPaymentTime()));
            pstmt.setInt(6, payment.getPaymentId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return payment;
    }
    
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM payments WHERE payment_id = ?";
        
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
        String sql = "SELECT COUNT(*) FROM payments WHERE payment_id = ?";
        
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
    
    // 额外的支付相关方法
    public Optional<Payment> findByOrderId(Integer orderId) {
        String sql = "SELECT * FROM payments WHERE order_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setOrderId(rs.getInt("order_id"));
                payment.setPaymentMethod(Payment.PaymentMethod.valueOf(rs.getString("payment_method")));
                payment.setPaymentAmount(rs.getBigDecimal("payment_amount"));
                payment.setPaymentStatus(Payment.PaymentStatus.valueOf(rs.getString("payment_status")));
                payment.setPaymentTime(rs.getTimestamp("payment_time").toLocalDateTime());
                
                return Optional.of(payment);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
} 