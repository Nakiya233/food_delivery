package com.example.food_delivery.dao;

import com.example.food_delivery.model.DeliveryStats;
import com.example.food_delivery.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeliveryStatsDAO implements BaseDAO<DeliveryStats, Integer> {
    
    @Override
    public DeliveryStats save(DeliveryStats stats) {
        String sql = "INSERT INTO delivery_stats (delivery_person, total_deliveries, successful_deliveries, " +
                    "failed_deliveries, avg_delivery_time, create_time, update_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, stats.getDeliveryPersonId());
            pstmt.setInt(2, stats.getTotalDeliveries());
            pstmt.setInt(3, stats.getSuccessfulDeliveries());
            pstmt.setInt(4, stats.getFailedDeliveries());
            pstmt.setDouble(5, stats.getAvgDeliveryTime());
            pstmt.setTimestamp(6, Timestamp.valueOf(stats.getCreateTime()));
            pstmt.setTimestamp(7, Timestamp.valueOf(stats.getUpdateTime()));
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating delivery stats failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    stats.setStatsId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating delivery stats failed, no ID obtained.");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return stats;
    }
    
    @Override
    public Optional<DeliveryStats> findById(Integer id) {
        String sql = "SELECT * FROM delivery_stats WHERE stats_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                DeliveryStats stats = new DeliveryStats();
                stats.setStatsId(rs.getInt("stats_id"));
                stats.setDeliveryPersonId(rs.getInt("delivery_person"));
                stats.setTotalDeliveries(rs.getInt("total_deliveries"));
                stats.setSuccessfulDeliveries(rs.getInt("successful_deliveries"));
                stats.setFailedDeliveries(rs.getInt("failed_deliveries"));
                stats.setAvgDeliveryTime(rs.getDouble("avg_delivery_time"));
                stats.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                stats.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                return Optional.of(stats);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<DeliveryStats> findAll() {
        List<DeliveryStats> statsList = new ArrayList<>();
        String sql = "SELECT * FROM delivery_stats";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                DeliveryStats stats = new DeliveryStats();
                stats.setStatsId(rs.getInt("stats_id"));
                stats.setDeliveryPersonId(rs.getInt("delivery_person"));
                stats.setTotalDeliveries(rs.getInt("total_deliveries"));
                stats.setSuccessfulDeliveries(rs.getInt("successful_deliveries"));
                stats.setFailedDeliveries(rs.getInt("failed_deliveries"));
                stats.setAvgDeliveryTime(rs.getDouble("avg_delivery_time"));
                stats.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                stats.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                statsList.add(stats);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return statsList;
    }
    
    @Override
    public DeliveryStats update(DeliveryStats stats) {
        String sql = "UPDATE delivery_stats SET delivery_person = ?, total_deliveries = ?, " +
                    "successful_deliveries = ?, failed_deliveries = ?, avg_delivery_time = ?, " +
                    "update_time = ? WHERE stats_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, stats.getDeliveryPersonId());
            pstmt.setInt(2, stats.getTotalDeliveries());
            pstmt.setInt(3, stats.getSuccessfulDeliveries());
            pstmt.setInt(4, stats.getFailedDeliveries());
            pstmt.setDouble(5, stats.getAvgDeliveryTime());
            pstmt.setTimestamp(6, Timestamp.valueOf(stats.getUpdateTime()));
            pstmt.setInt(7, stats.getStatsId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return stats;
    }
    
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM delivery_stats WHERE stats_id = ?";
        
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
        String sql = "SELECT COUNT(*) FROM delivery_stats WHERE stats_id = ?";
        
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
    
    // 额外的配送统计相关方法
    public Optional<DeliveryStats> findByDeliveryPerson(Integer deliveryPersonId) {
        String sql = "SELECT * FROM delivery_stats WHERE delivery_person = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, deliveryPersonId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                DeliveryStats stats = new DeliveryStats();
                stats.setStatsId(rs.getInt("stats_id"));
                stats.setDeliveryPersonId(rs.getInt("delivery_person"));
                stats.setTotalDeliveries(rs.getInt("total_deliveries"));
                stats.setSuccessfulDeliveries(rs.getInt("successful_deliveries"));
                stats.setFailedDeliveries(rs.getInt("failed_deliveries"));
                stats.setAvgDeliveryTime(rs.getDouble("avg_delivery_time"));
                stats.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                stats.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                return Optional.of(stats);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    public void updateDeliveryStats(Integer deliveryPersonId, boolean isSuccessful, double deliveryTime) {
        Optional<DeliveryStats> existingStats = findByDeliveryPerson(deliveryPersonId);
        
        DeliveryStats stats = existingStats.orElseGet(() -> {
            DeliveryStats newStats = new DeliveryStats();
            newStats.setDeliveryPersonId(deliveryPersonId);
            return newStats;
        });
        
        stats.setTotalDeliveries(stats.getTotalDeliveries() + 1);
        if (isSuccessful) {
            stats.setSuccessfulDeliveries(stats.getSuccessfulDeliveries() + 1);
        } else {
            stats.setFailedDeliveries(stats.getFailedDeliveries() + 1);
        }
        
        // 更新平均配送时间
        double currentTotal = stats.getAvgDeliveryTime() * (stats.getTotalDeliveries() - 1);
        stats.setAvgDeliveryTime((currentTotal + deliveryTime) / stats.getTotalDeliveries());
        
        stats.setUpdateTime(LocalDateTime.now());
        
        if (existingStats.isPresent()) {
            update(stats);
        } else {
            save(stats);
        }
    }
} 