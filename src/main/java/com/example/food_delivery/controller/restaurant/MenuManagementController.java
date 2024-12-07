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
import javafx.beans.property.SimpleStringProperty;
import com.example.food_delivery.dao.RestaurantDAO;
import com.example.food_delivery.model.Restaurant;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;

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
    @FXML
    private TableColumn<MenuItem, String> restaurantColumn;

    private MenuItemDAO menuItemDAO;

    @FXML
    private void initialize() {
        menuItemDAO = new MenuItemDAO();
        RestaurantDAO restaurantDAO = new RestaurantDAO();
        
        // 设置列的单元格值工厂
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        
        // 自定义餐厅列的值工厂，根据restaurantId查询餐厅名称
        restaurantColumn.setCellValueFactory(cellData -> {
            Integer restaurantId = cellData.getValue().getRestaurantId();
            String restaurantName = restaurantDAO.findById(restaurantId)
                    .map(Restaurant::getRestaurantName)
                    .orElse("未知餐厅");
            return new SimpleStringProperty(restaurantName);
        });
        
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
        try{
            // 创建对话框
            Dialog<MenuItem> dialog = new Dialog<>();
            dialog.setTitle(item == null ? "添加菜品" : "编辑菜品");
            dialog.setHeaderText(null);

            // 设置确认和取消按钮
            ButtonType saveButtonType = new ButtonType("保存", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // 创建表单内容
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField nameField = new TextField();
            TextField descriptionField = new TextField();
            TextField priceField = new TextField();
            TextField stockField = new TextField();
            TextField restaurantIdField = new TextField();

            // 如果是编辑模式，填充现有数据
            if (item != null) {
                nameField.setText(item.getItemName());
                descriptionField.setText(item.getDescription());
                priceField.setText(item.getPrice().toString());
                stockField.setText(String.valueOf(item.getStockQuantity()));
                restaurantIdField.setText(String.valueOf(item.getRestaurantId()));
            }

            grid.add(new Label("菜品名称:"), 0, 0);
            grid.add(nameField, 1, 0);
            grid.add(new Label("描述:"), 0, 1);
            grid.add(descriptionField, 1, 1);
            grid.add(new Label("价格:"), 0, 2);
            grid.add(priceField, 1, 2);
            grid.add(new Label("库存:"), 0, 3);
            grid.add(stockField, 1, 3);
            grid.add(new Label("餐厅ID:"), 0, 4);
            grid.add(restaurantIdField, 1, 4);

            dialog.getDialogPane().setContent(grid);

            // 设置结果转换器
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    try {
                        MenuItem menuItem = item == null ? new MenuItem() : item;
                        menuItem.setItemName(nameField.getText());
                        menuItem.setDescription(descriptionField.getText());
                        menuItem.setPrice(new BigDecimal(priceField.getText()));
                        menuItem.setStockQuantity(Integer.parseInt(stockField.getText()));
                        menuItem.setRestaurantId(Integer.parseInt(restaurantIdField.getText()));
                        return menuItem;
                    } catch (NumberFormatException e) {
                        showAlert("错误", "请输入有效的数字");
                        return null;
                    }
                }
                return null;
            });

            // 显示对话框并处理结果
            dialog.showAndWait().ifPresent(menuItem -> {
                if (item == null) {
                    menuItemDAO.save(menuItem);
                } else {
                    menuItemDAO.update(menuItem);
                }
                loadMenuItems();
            });

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("错误", "处理菜品时出现错误");
        }
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