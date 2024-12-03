package com.example.food_delivery.dao;

import com.example.food_delivery.model.MenuItem;
import com.example.food_delivery.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuItemDAO implements BaseDAO<MenuItem, Integer> {
    
    @Override
    public MenuItem save(MenuItem menuItem) {
        String sql = "INSERT INTO menu_items (restaurant_id, item_name, description, price, " +
                    "stock_quantity, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, menuItem.getRestaurantId());
            pstmt.setString(2, menuItem.getItemName());
            pstmt.setString(3, menuItem.getDescription());
            pstmt.setBigDecimal(4, menuItem.getPrice());
            pstmt.setInt(5, menuItem.getStockQuantity());
            pstmt.setTimestamp(6, Timestamp.valueOf(menuItem.getCreateTime()));
            pstmt.setTimestamp(7, Timestamp.valueOf(menuItem.getUpdateTime()));
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating menu item failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    menuItem.setMenuId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating menu item failed, no ID obtained.");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return menuItem;
    }
    
    @Override
    public Optional<MenuItem> findById(Integer id) {
        String sql = "SELECT * FROM menu_items WHERE menu_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                MenuItem menuItem = new MenuItem();
                menuItem.setMenuId(rs.getInt("menu_id"));
                menuItem.setRestaurantId(rs.getInt("restaurant_id"));
                menuItem.setItemName(rs.getString("item_name"));
                menuItem.setDescription(rs.getString("description"));
                menuItem.setPrice(rs.getBigDecimal("price"));
                menuItem.setStockQuantity(rs.getInt("stock_quantity"));
                menuItem.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                menuItem.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                return Optional.of(menuItem);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<MenuItem> findAll() {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                MenuItem menuItem = new MenuItem();
                menuItem.setMenuId(rs.getInt("menu_id"));
                menuItem.setRestaurantId(rs.getInt("restaurant_id"));
                menuItem.setItemName(rs.getString("item_name"));
                menuItem.setDescription(rs.getString("description"));
                menuItem.setPrice(rs.getBigDecimal("price"));
                menuItem.setStockQuantity(rs.getInt("stock_quantity"));
                menuItem.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                menuItem.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                menuItems.add(menuItem);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return menuItems;
    }
    
    @Override
    public MenuItem update(MenuItem menuItem) {
        String sql = "UPDATE menu_items SET restaurant_id = ?, item_name = ?, description = ?, " +
                    "price = ?, stock_quantity = ?, update_time = ? WHERE menu_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, menuItem.getRestaurantId());
            pstmt.setString(2, menuItem.getItemName());
            pstmt.setString(3, menuItem.getDescription());
            pstmt.setBigDecimal(4, menuItem.getPrice());
            pstmt.setInt(5, menuItem.getStockQuantity());
            pstmt.setTimestamp(6, Timestamp.valueOf(menuItem.getUpdateTime()));
            pstmt.setInt(7, menuItem.getMenuId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return menuItem;
    }
    
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM menu_items WHERE menu_id = ?";
        
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
        String sql = "SELECT COUNT(*) FROM menu_items WHERE menu_id = ?";
        
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
    
    // 额外的菜单项相关方法
    public List<MenuItem> findByRestaurantId(Integer restaurantId) {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE restaurant_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, restaurantId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                MenuItem menuItem = new MenuItem();
                menuItem.setMenuId(rs.getInt("menu_id"));
                menuItem.setRestaurantId(rs.getInt("restaurant_id"));
                menuItem.setItemName(rs.getString("item_name"));
                menuItem.setDescription(rs.getString("description"));
                menuItem.setPrice(rs.getBigDecimal("price"));
                menuItem.setStockQuantity(rs.getInt("stock_quantity"));
                menuItem.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                menuItem.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                menuItems.add(menuItem);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return menuItems;
    }
    
    public List<MenuItem> findByPriceRange(double minPrice, double maxPrice) {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE price BETWEEN ? AND ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, minPrice);
            pstmt.setDouble(2, maxPrice);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                MenuItem menuItem = new MenuItem();
                menuItem.setMenuId(rs.getInt("menu_id"));
                menuItem.setRestaurantId(rs.getInt("restaurant_id"));
                menuItem.setItemName(rs.getString("item_name"));
                menuItem.setDescription(rs.getString("description"));
                menuItem.setPrice(rs.getBigDecimal("price"));
                menuItem.setStockQuantity(rs.getInt("stock_quantity"));
                menuItem.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                menuItem.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                
                menuItems.add(menuItem);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return menuItems;
    }
} 