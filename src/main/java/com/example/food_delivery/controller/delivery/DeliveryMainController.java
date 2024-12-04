package com.example.food_delivery.controller.delivery;

import com.example.food_delivery.controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DeliveryMainController {
    @FXML
    private TableView<String> availableOrdersTable;
    @FXML
    private TableView<String> myDeliveriesTable;

    @FXML
    private void initialize() {
        // 初始化组件
    }

    @FXML
    private void showAvailableOrders() {
        // 显示可接订单
    }

    @FXML
    private void showMyDeliveries() {
        // 显示我的配送
    }

    @FXML
    private void showProfile() {
        // 显示个人信息
    }

    @FXML
    private void logout() {
        LoginController.logout(availableOrdersTable);
    }
} 