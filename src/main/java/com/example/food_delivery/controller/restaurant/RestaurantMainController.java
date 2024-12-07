package com.example.food_delivery.controller.restaurant;

import com.example.food_delivery.controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu-management.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) pendingOrdersTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("无法打开菜单管理界面: " + e.getMessage());
            alert.showAndWait();
        }
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 