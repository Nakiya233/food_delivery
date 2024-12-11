package com.example.food_delivery.controller.customer;

import com.example.food_delivery.controller.LoginController;
import com.example.food_delivery.model.Restaurant;
import com.example.food_delivery.dao.RestaurantDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import com.example.food_delivery.model.CartItem;
import java.math.BigDecimal;
import com.example.food_delivery.model.MenuItem;

public class CustomerMainController {
    @FXML
    private ListView<Restaurant> restaurantListView;
    @FXML
    private ListView<CartItem> cartListView;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalPriceLabel;

    private RestaurantDAO restaurantDAO;
    private ObservableList<Restaurant> restaurants;
    private ObservableList<CartItem> cartItems;
    private BigDecimal totalPrice = BigDecimal.ZERO;

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

        restaurantListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // 双击事件
                Restaurant selectedRestaurant = restaurantListView.getSelectionModel().getSelectedItem();
                if (selectedRestaurant != null) {
                    showRestaurantMenu(selectedRestaurant);
                }
            }
        });

        cartItems = FXCollections.observableArrayList();
        cartListView.setItems(cartItems);
        updateTotalPrice();

        // 自定义单元格显示
        cartListView.setCellFactory(lv -> new ListCell<CartItem>() {
            @Override
            protected void updateItem(CartItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%s x%d ¥%s",
                            item.getMenuItem().getItemName(),
                            item.getQuantity(),
                            item.getMenuItem().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))));
                }
            }
        });
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
        if (cartItems.isEmpty()) {
            showAlert("提示", "购物车为空");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认支付");
        alert.setHeaderText(null);
        alert.setContentText("总金额: ¥" + totalPrice + "\n确认支付?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // TODO: 实现实际的支付逻辑
                cartItems.clear();
                updateTotalPrice();
                showAlert("成功", "支付成功");
            }
        });
    }

    @FXML
    private void handleClearCart() {
        cartItems.clear();
        updateTotalPrice();
    }

    @FXML
    private void showProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/profile-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) restaurantListView.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("错误", "无法打开个人信息界面: " + e.getMessage());
        }
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

    private void showRestaurantMenu(Restaurant restaurant) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/restaurant-menu.fxml"));
            Scene scene = new Scene(loader.load());
            
            RestaurantMenuController controller = loader.getController();
            controller.setRestaurant(restaurant);
            
            // 将当前控制器实例存储在Scene的userData中
            scene.setUserData(this);
            
            Stage stage = (Stage) restaurantListView.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("错误", "无法打开餐厅菜单: " + e.getMessage());
        }
    }

    public void addToCart(MenuItem menuItem) {
        if (menuItem == null) {
            showAlert("错误", "无效的菜品");
            return;
        }

        try {
            // 检查购物车中是否已存在该商品
            boolean found = false;
            for (CartItem item : cartItems) {
                if (item.getMenuItem().getMenuId().equals(menuItem.getMenuId())) {
                    item.setQuantity(item.getQuantity() + 1);
                    found = true;
                    break;
                }
            }

            // 如果不存在，添加新的购物车项
            if (!found) {
                cartItems.add(new CartItem(menuItem, 1));
            }

            // 强制刷新UI
            cartListView.refresh();
            updateTotalPrice();
            
            // 可选：打印调试信息
            // System.out.println("购物车项数量: " + cartItems.size());
            // for (CartItem item : cartItems) {
            //     System.out.println("项目: " + item.getMenuItem().getItemName() + " x" + item.getQuantity());
            // }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("错误", "添加到购物车失败: " + e.getMessage());
        }
    }

    private void updateTotalPrice() {
        totalPrice = cartItems.stream()
            .map(item -> item.getMenuItem().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalPriceLabel.setText(String.format("¥%.2f", totalPrice));
        cartListView.refresh();
    }

    public ObservableList<CartItem> getCartItems() {
        return cartItems;
    }
} 