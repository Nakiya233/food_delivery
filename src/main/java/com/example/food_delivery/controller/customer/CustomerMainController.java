package com.example.food_delivery.controller.customer;

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
    }

    @FXML
    private void showMyOrders() {
        // 显示我的订单
    }

    @FXML
    private void showOrderHistory() {
        // 显示订单历史
    }

    @FXML
    private void showProfile() {
        // 显示个人信息
    }

    @FXML
    private void logout() {
        // 退出登录
    }
}