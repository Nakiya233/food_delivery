package com.example.food_delivery.controller.restaurant;

import com.example.food_delivery.controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RestaurantMainController {
    @FXML
    private TableView<String> pendingOrdersTable;
    @FXML
    private Label todayOrdersLabel;
    @FXML
    private Label todayRevenueLabel;
    @FXML
    private Label pendingOrdersLabel;

    @FXML
    private void initialize() {
        // 初始化组件
    }

    @FXML
    private void showMenuManagement() {
        // 显示菜单管理
    }

    @FXML
    private void showOrderManagement() {
        // 显示订单管理
    }

    @FXML
    private void showSalesStatistics() {
        // 显示销售统计
    }

    @FXML
    private void showProfile() {
        // 显示商家信息
    }

    @FXML
    private void logout() {
        LoginController.logout(pendingOrdersTable);
    }
} 