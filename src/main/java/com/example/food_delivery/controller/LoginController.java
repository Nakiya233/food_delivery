package com.example.food_delivery.controller;

import com.example.food_delivery.util.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> userTypeComboBox;

    private static String currentUserName;

    @FXML
    private void initialize() {
        userTypeComboBox.getItems().addAll("顾客", "商家", "配送员");
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String userType = userTypeComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || userType == null) {
            showAlert("错误", "请填写完整信息");
            return;
        }

        if (!validateUserType(username,password, userType)) {
            showAlert("错误", "用户类型不匹配或密码错误");
            return;
        }

        try {
            String fxmlFile = switch (userType) {
                case "顾客" -> "/customer-main.fxml";
                case "商家" -> "/restaurant-main.fxml";
                case "配送员" -> "/delivery-main.fxml";
                default -> throw new IllegalStateException("未知用户类型");
            };
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            setCurrentUserName(username);
            
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("错误", "登录失败: " + e.getMessage());
        }
    }

    private boolean validateUserType(String username, String password, String userType) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // 查询用户的密码和用户类型
            String sql = "SELECT password, user_type FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String actualPassword = rs.getString("password"); // 从结果集中获取实际密码
                String actualUserType = rs.getString("user_type"); // 从结果集中获取实际用户类型

                // 验证密码
                if (isPasswordCorrect(password, actualPassword)) {
                    // 密码正确时，继续验证用户类型
                    return switch (userType) {
                        case "顾客" -> actualUserType.equals("顾客");
                        case "商家" -> actualUserType.equals("商家");
                        case "配送员" -> actualUserType.equals("配送员");
                        default -> false;
                    };
                }
            }
            return false; // 如果没有找到用户，或者密码不正确，返回 false
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean isPasswordCorrect(String inputPassword, String storedPassword) {
        // 通常使用哈希算法进行密码比较，这里简单示范：
        return inputPassword.equals(storedPassword); // 注意：这仅为示例，真实情况下应使用加密比较
    }

    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/register-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("错误", "无法打开注册界面");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void logout(Control control) {
        try {
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/login-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) control.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("退出登录失败: " + e.getMessage());
            alert.showAndWait();
        }
    }
    public static String getCurrentUserName() {
        return currentUserName;
    }
    
    public static void setCurrentUserName(String userName) {
        currentUserName = userName;
    }
} 