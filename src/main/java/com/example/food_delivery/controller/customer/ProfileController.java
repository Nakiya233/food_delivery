package com.example.food_delivery.controller.customer;

import com.example.food_delivery.model.User;

import java.util.Optional;

import com.example.food_delivery.controller.LoginController;
import com.example.food_delivery.dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ProfileController {
    @FXML
    private Label userIdLabel;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;

    private UserDAO userDAO;
    private User currentUser;

    @FXML
    private void initialize() {
        userDAO = new UserDAO();
        loadUserInfo();
    }

    private void loadUserInfo() {
        try {
            // 获取当前登录用户ID
            String currentUserId = LoginController.getCurrentUserName();
            if (currentUserId == null) {
                showAlert("错误", "未找到登录用户信息");
                handleBack();
                return;
            }

            // 从数据库获取用户信息
            Optional<User> userOptional = userDAO.findByUsername(currentUserId);
            if (userOptional.isPresent()) {
                currentUser = userOptional.get();
                // 显示用户信息
                userIdLabel.setText(currentUser.getUsername());
                phoneField.setText(currentUser.getPhone());
                addressField.setText(currentUser.getAddress());
            } else {
                showAlert("错误", "未找到用户信息");
                handleBack();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("错误", "加载用户信息失败: " + e.getMessage());
            handleBack();
        }
    }

    @FXML
    private void handleSave() {
        try {
            // 获取用户输入的新信息
            String newPhone = phoneField.getText().trim();
            String newAddress = addressField.getText().trim();

            // 基本验证
            if (newPhone.isEmpty() || newAddress.isEmpty()) {
                showAlert("错误", "电话号码和地址不能为空");
                return;
            }

            // 更新当前用户对象
            currentUser.setPhone(newPhone);
            currentUser.setAddress(newAddress);

            // 保存到数据库
            userDAO.update(currentUser);

            showAlert("提示", "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("错误", "保存失败: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/customer-main.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) userIdLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("错误", "返回失败: " + e.getMessage());
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