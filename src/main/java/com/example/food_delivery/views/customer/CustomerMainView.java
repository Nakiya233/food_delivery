package com.example.food_delivery.views.customer;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class CustomerMainView extends BorderPane {
    
    private ListView<String> restaurantListView;
    private ListView<String> cartListView;
    private TextField searchField;
    private Label totalPriceLabel;
    private Button checkoutButton;
    
    public CustomerMainView() {
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        // 初始化组件
        restaurantListView = new ListView<>();
        cartListView = new ListView<>();
        searchField = new TextField();
        searchField.setPromptText("搜索餐厅");
        totalPriceLabel = new Label("¥0.00");
        checkoutButton = new Button("结算");
        
        // 创建菜单栏
        MenuBar menuBar = new MenuBar();
        Menu orderMenu = new Menu("订单");
        Menu profileMenu = new Menu("个人");
        
        MenuItem myOrdersItem = new MenuItem("我的订单");
        MenuItem orderHistoryItem = new MenuItem("订单历史");
        MenuItem profileItem = new MenuItem("个人信息");
        MenuItem logoutItem = new MenuItem("退出登录");
        
        orderMenu.getItems().addAll(myOrdersItem, orderHistoryItem);
        profileMenu.getItems().addAll(profileItem, logoutItem);
        menuBar.getMenus().addAll(orderMenu, profileMenu);
        
        setTop(menuBar);
    }
    
    private void setupLayout() {
        // 设置中心区域
        TabPane tabPane = new TabPane();
        
        // 餐厅列表标签页
        Tab restaurantsTab = new Tab("餐厅列表");
        restaurantsTab.setClosable(false);
        VBox restaurantsBox = new VBox(10);
        restaurantsBox.setPadding(new Insets(10));
        
        HBox searchBox = new HBox(10);
        Button searchButton = new Button("搜索");
        searchBox.getChildren().addAll(searchField, searchButton);
        
        restaurantsBox.getChildren().addAll(searchBox, restaurantListView);
        restaurantsTab.setContent(restaurantsBox);
        
        // 购物车标签页
        Tab cartTab = new Tab("购物车");
        cartTab.setClosable(false);
        VBox cartBox = new VBox(10);
        cartBox.setPadding(new Insets(10));
        
        HBox checkoutBox = new HBox(10);
        checkoutBox.getChildren().addAll(new Label("总计: "), totalPriceLabel, checkoutButton);
        
        cartBox.getChildren().addAll(cartListView, checkoutBox);
        cartTab.setContent(cartBox);
        
        tabPane.getTabs().addAll(restaurantsTab, cartTab);
        setCenter(tabPane);
    }
    
    // Getters
    public ListView<String> getRestaurantListView() {
        return restaurantListView;
    }
    
    public ListView<String> getCartListView() {
        return cartListView;
    }
    
    public TextField getSearchField() {
        return searchField;
    }
    
    public Label getTotalPriceLabel() {
        return totalPriceLabel;
    }
    
    public Button getCheckoutButton() {
        return checkoutButton;
    }
} 