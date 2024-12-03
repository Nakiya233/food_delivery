package com.example.food_delivery.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.example.food_delivery.util.DatabaseConnection;

public class RegisterController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private ComboBox<String> userTypeComboBox;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;

    @FXML
    private void initialize() {
        userTypeComboBox.getItems().addAll("顾客", "商家", "配送员");
    }

    @FXML
    private void handleRegister() {
        // 验证输入
        if (!validateInput()) {
            return;
        }

        // 检查用户名是否已存在
        if (isUsernameExists(usernameField.getText())) {
            showAlert("错误", "用户名已存在");
            return;
        }

        // 执行注册
        if (registerUser()) {
            showAlert("成功", "注册成功");
            handleBack(); // 返回登录界面
        } else {
            showAlert("错误", "注册失败，请重试");
        }
    }

    private boolean validateInput() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String userType = userTypeComboBox.getValue();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || 
            userType == null || phone.isEmpty() || email.isEmpty()) {
            showAlert("错误", "请填写所有字段");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("错误", "两次输入的密码不一致");
            return false;
        }

        if (!phone.matches("\\d{11}")) {
            showAlert("错误", "请输入有效的手机号码");
            return false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert("错误", "请输入有效的电子邮箱");
            return false;
        }

        return true;
    }

    private boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean registerUser() {
        String sql = "INSERT INTO users (username, password, user_type, phone, email, create_time) " +
                    "VALUES (?, ?, ?, ?, ?, NOW())";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usernameField.getText());
            pstmt.setString(2, passwordField.getText()); // 实际应用中应该加密存储
            pstmt.setString(3, userTypeComboBox.getValue());
            pstmt.setString(4, phoneField.getText());
            pstmt.setString(5, emailField.getText());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 