package com.example.food_delivery.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> userTypeComboBox;

    @FXML
    private void initialize() {
        userTypeComboBox.getItems().addAll("顾客", "商家", "配送员");
    }

    @FXML
    private void handleLogin() {
        String userType = userTypeComboBox.getValue();
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
            
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("错误", "登录失败: " + e.getMessage());
        }
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
} 