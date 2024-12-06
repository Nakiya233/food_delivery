package com.example.food_delivery.controller.customer;

import com.example.food_delivery.controller.LoginController;
import com.example.food_delivery.model.Restaurant;
import com.example.food_delivery.dao.RestaurantDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class CustomerMainController {
    @FXML
    private ListView<Restaurant> restaurantListView;
    @FXML
    private ListView<String> cartListView;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalPriceLabel;

    private RestaurantDAO restaurantDAO;
    private ObservableList<Restaurant> restaurants;

    @FXML
    private void initialize() {
        restaurantDAO = new RestaurantDAO();
        restaurants = FXCollections.observableArrayList();
        restaurantListView.setItems(restaurants);
        
        // 设置餐厅列表的单元格显示格式
        restaurantListView.setCellFactory(param -> new ListCell<Restaurant>() {
            @Override
            protected void updateItem(Restaurant restaurant, boolean empty) {
                super.updateItem(restaurant, empty);
                if (empty || restaurant == null) {
                    setText(null);
                } else {
                    String displayText = String.format("%s\n地址: %s\n评分: %.1f",
                        restaurant.getRestaurantName(),
                        restaurant.getLocation(),
                        restaurant.getRating());
                    setText(displayText);
                }
            }
        });

        loadRestaurants();
    }

    private void loadRestaurants() {
        try {
            List<Restaurant> restaurantList = restaurantDAO.findAll();
            restaurants.clear();
            restaurants.addAll(restaurantList);
        } catch (Exception e) {
            showAlert("错误", "加载餐厅列表失败: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().trim();
        try {
            List<Restaurant> searchResults = restaurantDAO.searchByName(query);
            restaurants.clear();
            restaurants.addAll(searchResults);
        } catch (Exception e) {
            showAlert("错误", "搜索失败: " + e.getMessage());
        }
    }

    @FXML
    private void showMyOrders() {
        showAlert("提示", "我的订单功能尚未实现");
    }

    @FXML
    private void showOrderHistory() {
        showAlert("提示", "订单历史功能尚未实现");
    }

    @FXML
    private void handleCheckout() {
        showAlert("提示", "结算功能尚未实现");
    }

    @FXML
    private void showProfile() {
        showAlert("提示", "个人信息功能尚未实现");
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