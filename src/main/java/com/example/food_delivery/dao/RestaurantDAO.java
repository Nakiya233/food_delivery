package com.example.food_delivery.dao;

import com.example.food_delivery.model.Restaurant;
import com.example.food_delivery.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestaurantDAO implements BaseDAO<Restaurant, Integer> {
    
    @Override
    public Restaurant save(Restaurant restaurant) {
        String sql = "INSERT INTO restaurants (restaurant_name, location, phone, rating, create_time, update_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, restaurant.getRestaurantName());
            pstmt.setString(2, restaurant.getLocation());
            pstmt.setString(3, restaurant.getPhone());
            pstmt.setDouble(4, restaurant.getRating());
            pstmt.setTimestamp(5, Timestamp.valueOf(restaurant.getCreateTime()));
            pstmt.setTimestamp(6, Timestamp.valueOf(restaurant.getUpdateTime()));
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating restaurant failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    restaurant.setRestaurantId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating restaurant failed, no ID obtained.");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return restaurant;
    }
    
    @Override
    public Optional<Restaurant> findById(Integer id) {
        String sql = "SELECT * FROM restaurants WHERE restaurant_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getInt("restaurant_id"));
                restaurant.setRestaurantName(rs.getString("restaurant_name"));
                restaurant.setLocation(rs.getString("location"));
                restaurant.setPhone(rs.getString("phone"));
                restaurant.setRating(rs.getDouble("rating"));
                restaurant.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                restaurant.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                return Optional.of(restaurant);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getInt("restaurant_id"));
                restaurant.setRestaurantName(rs.getString("restaurant_name"));
                restaurant.setLocation(rs.getString("location"));
                restaurant.setPhone(rs.getString("phone"));
                restaurant.setRating(rs.getDouble("rating"));
                restaurant.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                restaurant.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                restaurants.add(restaurant);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return restaurants;
    }
    
    @Override
    public Restaurant update(Restaurant restaurant) {
        String sql = "UPDATE restaurants SET restaurant_name = ?, location = ?, phone = ?, " +
                    "rating = ?, update_time = ? WHERE restaurant_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, restaurant.getRestaurantName());
            pstmt.setString(2, restaurant.getLocation());
            pstmt.setString(3, restaurant.getPhone());
            pstmt.setDouble(4, restaurant.getRating());
            pstmt.setTimestamp(5, Timestamp.valueOf(restaurant.getUpdateTime()));
            pstmt.setInt(6, restaurant.getRestaurantId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return restaurant;
    }
    
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM restaurants WHERE restaurant_id = ?";
        
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
        String sql = "SELECT COUNT(*) FROM restaurants WHERE restaurant_id = ?";
        
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
    
    // 额外的餐厅相关方法
    public List<Restaurant> findByRating(double minRating) {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants WHERE rating >= ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, minRating);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getInt("restaurant_id"));
                restaurant.setRestaurantName(rs.getString("restaurant_name"));
                restaurant.setLocation(rs.getString("location"));
                restaurant.setPhone(rs.getString("phone"));
                restaurant.setRating(rs.getDouble("rating"));
                restaurant.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                restaurant.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                restaurants.add(restaurant);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return restaurants;
    }
    
    public List<Restaurant> searchByName(String query) {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants WHERE restaurant_name LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getInt("restaurant_id"));
                restaurant.setRestaurantName(rs.getString("restaurant_name"));
                restaurant.setLocation(rs.getString("location"));
                restaurant.setPhone(rs.getString("phone"));
                restaurant.setRating(rs.getDouble("rating"));
                restaurant.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                restaurant.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                restaurants.add(restaurant);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return restaurants;
    }
} 