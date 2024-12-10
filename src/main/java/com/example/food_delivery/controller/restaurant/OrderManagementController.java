package com.example.food_delivery.controller.restaurant;

import com.example.food_delivery.dao.OrderDAO;
import com.example.food_delivery.dao.UserDAO;
import com.example.food_delivery.model.Order;
import com.example.food_delivery.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderManagementController {
    @FXML
    private TableView<Order> orderTable;
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
    private TableColumn<Order, Void> actionColumn;

    private OrderDAO orderDAO;
    private UserDAO userDAO;

    @FXML
    private void initialize() {
        orderDAO = new OrderDAO();
        userDAO = new UserDAO();

        setupColumns();
        loadOrders();
    }

    private void setupColumns() {
        // 设置订单ID列
        orderIdColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.valueOf(cellData.getValue().getOrderId())));
            
        // 设置顾客列
        customerColumn.setCellValueFactory(cellData -> {
            Integer userId = cellData.getValue().getUserId();
            String customerName = userDAO.findById(userId)
                    .map(User::getUsername)
                    .orElse("未知用户");
            return new SimpleStringProperty(customerName);
        });

        // 设置时间列
        timeColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCreateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

        // 设置金额列
        amountColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTotalPrice().toString()));

        // 设置状态列
        statusColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getOrderStatus().getValue()));

        // 设置操作列
        setupActionColumn();
    }

    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button viewButton = new Button("查看");
            private final Button updateButton = new Button("更新状态");
            private final HBox box = new HBox(5, viewButton, updateButton);

            {
                viewButton.setOnAction(event -> {
                    Order order = getTableView().getItems().get(getIndex());
                    handleViewOrder(order);
                });

                updateButton.setOnAction(event -> {
                    Order order = getTableView().getItems().get(getIndex());
                    handleUpdateStatus(order);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });
    }

    private void loadOrders() {
        List<Order> orders = orderDAO.findAll();
        orderTable.getItems().setAll(orders);
    }

    private void handleViewOrder(Order order) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("订单详情");
        alert.setHeaderText("订单号: " + order.getOrderId());
        
        String content = String.format("""
            顾客: %s
            下单时间: %s
            订单金额: %s
            订单状态: %s
            """,
            userDAO.findById(order.getUserId()).map(User::getUsername).orElse("未知用户"),
            order.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            order.getTotalPrice(),
            order.getOrderStatus().getValue()
        );
        
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void handleUpdateStatus(Order order) {
        ChoiceDialog<Order.OrderStatus> dialog = new ChoiceDialog<>(
            order.getOrderStatus(),
            Order.OrderStatus.values()
        );
        
        dialog.setTitle("更新订单状态");
        dialog.setHeaderText("订单号: " + order.getOrderId());
        dialog.setContentText("选择新的订单状态:");
        
        dialog.showAndWait().ifPresent(selectedStatus -> {
            if (selectedStatus != null) {
                order.setOrderStatus(selectedStatus);
                orderDAO.update(order);
                loadOrders();
            }
        });
    }

    @FXML
    public void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant-main.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) orderTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("无法返回主界面: " + e.getMessage());
            alert.showAndWait();
        }
    }
} 