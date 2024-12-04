package com.example.food_delivery.controller.customer;

import com.example.food_delivery.controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CustomerMainController {
    @FXML
    private ListView<String> restaurantListView;
    @FXML
    private ListView<String> cartListView;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalPriceLabel;

    @FXML
    private void initialize() {
        // 初始化组件
    }

    @FXML
    private void handleSearch() {
        // 搜索功能
    }

    @FXML
    private void handleCheckout() {
        // 结算功能
        showAlert("提示", "结算功能尚未实现");
    }

    @FXML
    private void showMyOrders() {
        // 显示我的订单
        showAlert("提示", "查看我的订单功能尚未实现");
    }

    @FXML
    private void showOrderHistory() {
        // 显示订单历史
        showAlert("提示", "查看订单历史功能尚未实现");
    }

    @FXML
    private void showProfile() {
        // 显示个人信息
        showAlert("提示", "查看个人信息功能尚未实现");
    }

    @FXML
    private void logout() {
        LoginController.logout(restaurantListView);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 