package com.example.food_delivery.controller.customer;

import com.example.food_delivery.model.MenuItem;
import com.example.food_delivery.model.Restaurant;
import com.example.food_delivery.model.Review;
import com.example.food_delivery.model.User;
import com.example.food_delivery.dao.MenuItemDAO;
import com.example.food_delivery.dao.ReviewDAO;
import com.example.food_delivery.dao.UserDAO;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.beans.property.SimpleStringProperty;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RestaurantMenuController {
    @FXML
    private Label restaurantNameLabel;
    @FXML
    private Label restaurantInfoLabel;
    @FXML
    private TableView<MenuItem> menuItemTable;
    @FXML
    private TableColumn<MenuItem, String> nameColumn;
    @FXML
    private TableColumn<MenuItem, String> descriptionColumn;
    @FXML
    private TableColumn<MenuItem, String> priceColumn;
    @FXML
    private TableColumn<MenuItem, Void> actionColumn;
    @FXML
    private TableView<Review> reviewTable;
    @FXML
    private TableColumn<Review, String> reviewUserColumn;
    @FXML
    private TableColumn<Review, Integer> reviewRatingColumn;
    @FXML
    private TableColumn<Review, String> reviewCommentColumn;
    @FXML
    private TableColumn<Review, String> reviewTimeColumn;

    private Restaurant restaurant;
    private MenuItemDAO menuItemDAO;
    private ReviewDAO reviewDAO;
private UserDAO userDAO;

    @FXML
private void initialize() {
    menuItemDAO = new MenuItemDAO();
    reviewDAO = new ReviewDAO();
    userDAO = new UserDAO();
    
    // 设置菜单列的值工厂
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    priceColumn.setCellValueFactory(cellData -> 
        new SimpleStringProperty("¥" + cellData.getValue().getPrice().toString()));
    
    // 设置评论列的值工厂
    reviewUserColumn.setCellValueFactory(cellData -> {
        Integer userId = cellData.getValue().getUserId();
        String username = userDAO.findById(userId)
                .map(User::getUsername)
                .orElse("未知用户");
        return new SimpleStringProperty(username);
    });
    
    reviewRatingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
    reviewCommentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
    reviewTimeColumn.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().getCreateTime()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
    
    // 设置添加到购物车按钮
    setupActionColumn();
}

public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
    updateRestaurantInfo();
    loadMenuItems();
    loadReviews();
}

private void loadReviews() {
    // 获取与该餐厅相关的所有订单的评论
    List<Review> reviews = reviewDAO.findByRestaurantId(restaurant.getRestaurantId());
    reviewTable.getItems().setAll(reviews);
}

    // public void setRestaurant(Restaurant restaurant) {
    //     this.restaurant = restaurant;
    //     updateRestaurantInfo();
    //     loadMenuItems();
    // }

    private void updateRestaurantInfo() {
        restaurantNameLabel.setText(restaurant.getRestaurantName());
        restaurantInfoLabel.setText(String.format("地址: %s    评分: %.1f",
            restaurant.getLocation(), restaurant.getRating()));
    }

    private void loadMenuItems() {
        List<MenuItem> menuItems = menuItemDAO.findByRestaurantId(restaurant.getRestaurantId());
        menuItemTable.getItems().setAll(menuItems);
    }

    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button addButton = new Button("加入购物车");

            {
                addButton.setOnAction(event -> {
                    MenuItem item = getTableView().getItems().get(getIndex());
                    handleAddToCart(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : addButton);
            }
        });
    }

    private void handleAddToCart(MenuItem item) {
        // TODO: 实现添加到购物车的功能
        showAlert("提示", "添加到购物车功能尚未实现");
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/customer-main.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) restaurantNameLabel.getScene().getWindow();
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