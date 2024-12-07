package com.example.food_delivery.controller.restaurant;

import com.example.food_delivery.model.MenuItem;
import com.example.food_delivery.dao.MenuItemDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.math.BigDecimal;
import java.util.List;

public class MenuManagementController {
    @FXML
    private TableView<MenuItem> menuItemTable;
    @FXML
    private TableColumn<MenuItem, String> nameColumn;
    @FXML
    private TableColumn<MenuItem, String> descriptionColumn;
    @FXML
    private TableColumn<MenuItem, BigDecimal> priceColumn;
    @FXML
    private TableColumn<MenuItem, Integer> stockColumn;
    @FXML
    private TableColumn<MenuItem, Void> actionColumn;

    private MenuItemDAO menuItemDAO;

    @FXML
    private void initialize() {
        menuItemDAO = new MenuItemDAO();
        
        // 设置列的单元格值工厂
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        
        // 设置操作列
        setupActionColumn();
        
        // 加载菜品数据
        loadMenuItems();
    }

    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("编辑");
            private final Button deleteButton = new Button("删除");
            private final HBox box = new HBox(5, editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    MenuItem item = getTableView().getItems().get(getIndex());
                    handleEditMenuItem(item);
                });

                deleteButton.setOnAction(event -> {
                    MenuItem item = getTableView().getItems().get(getIndex());
                    handleDeleteMenuItem(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });
    }

    private void loadMenuItems() {
        List<MenuItem> menuItems = menuItemDAO.findAll();
        menuItemTable.getItems().setAll(menuItems);
    }

    @FXML
    private void handleAddMenuItem() {
        showMenuItemDialog(null);
    }

    private void handleEditMenuItem(MenuItem item) {
        showMenuItemDialog(item);
    }

    private void handleDeleteMenuItem(MenuItem item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setHeaderText(null);
        alert.setContentText("确定要删除这个菜品吗？");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                menuItemDAO.delete(item.getMenuId());
                loadMenuItems();
            }
        });
    }

    private void showMenuItemDialog(MenuItem item) {
        // 显示添加/编辑菜品对话框
        // 这部分将在后续实现
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant-main.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) menuItemTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("错误", "无法返回主界面");
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