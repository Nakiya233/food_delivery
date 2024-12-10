package com.example.food_delivery.controller.restaurant;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.food_delivery.controller.LoginController;
import com.example.food_delivery.dao.OrderDAO;
import com.example.food_delivery.dao.UserDAO;
import com.example.food_delivery.model.Order;
import com.example.food_delivery.model.User;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.control.Control;

public class RestaurantMainController {
    @FXML
    private TableView<Order> pendingOrdersTable;
    @FXML
    private TableColumn<Order, String> orderIdColumn;
    @FXML
    private TableColumn<Order, String> customerColumn;
    @FXML
    private TableColumn<Order, String> timeColumn;
    @FXML
    private TableColumn<Order, String> amountColumn;
    @FXML
    private TableColumn<Order, String> statusColumn;
    @FXML
    private Label todayOrdersLabel;
    @FXML
    private Label todayRevenueLabel;
    @FXML
    private Label pendingOrdersLabel;


    private UserDAO userDAO;

    private OrderDAO orderDAO;

    @FXML
    private void initialize() {
        orderDAO = new OrderDAO();
        userDAO = new UserDAO();
        // 初始化组件
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        customerColumn.setCellValueFactory(cellData -> {
            Integer userId = cellData.getValue().getUserId();
            String customerName = userDAO.findById(userId)
                    .map(User::getUsername)
                    .orElse("未知用户");
            return new SimpleStringProperty(customerName);
        });

        // 格式化时间显示
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getCreateTime().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

        // 订单状态显示
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getOrderStatus().getValue()));

        amountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getTotalPrice().toString()));

        // 加载待处理订单数据
        loadPendingOrders();
    }

    private void loadPendingOrders() {
        List<Order> orders = orderDAO.findAll();
        pendingOrdersTable.getItems().setAll(orders);
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/order-management.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) pendingOrdersTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("错误", "无法打开订单管理界面: " + e.getMessage());
        }
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