package com.example.food_delivery.views.delivery;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class DeliveryMainView extends BorderPane {
    
    private TableView<String> availableOrdersTable;
    private TableView<String> myDeliveriesTable;
    
    public DeliveryMainView() {
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        // 初始化组件
        availableOrdersTable = new TableView<>();
        myDeliveriesTable = new TableView<>();
        
        // 创建菜单栏
        MenuBar menuBar = new MenuBar();
        Menu orderMenu = new Menu("订单");
        Menu accountMenu = new Menu("账户");
        
        MenuItem availableOrdersItem = new MenuItem("可接订单");
        MenuItem myDeliveriesItem = new MenuItem("我的配送");
        MenuItem profileItem = new MenuItem("个人信息");
        MenuItem logoutItem = new MenuItem("退出登录");
        
        orderMenu.getItems().addAll(availableOrdersItem, myDeliveriesItem);
        accountMenu.getItems().addAll(profileItem, logoutItem);
        
        menuBar.getMenus().addAll(orderMenu, accountMenu);
        setTop(menuBar);
    }
    
    private void setupLayout() {
        // 设置中心区域
        TabPane tabPane = new TabPane();
        
        // 可接订单标签页
        Tab availableOrdersTab = new Tab("可接订单");
        availableOrdersTab.setClosable(false);
        VBox availableOrdersBox = new VBox(10);
        availableOrdersBox.setPadding(new Insets(10));
        availableOrdersBox.getChildren().add(availableOrdersTable);
        availableOrdersTab.setContent(availableOrdersBox);
        
        // 我的配送标签页
        Tab myDeliveriesTab = new Tab("我的配送");
        myDeliveriesTab.setClosable(false);
        VBox myDeliveriesBox = new VBox(10);
        myDeliveriesBox.setPadding(new Insets(10));
        myDeliveriesBox.getChildren().add(myDeliveriesTable);
        myDeliveriesTab.setContent(myDeliveriesBox);
        
        tabPane.getTabs().addAll(availableOrdersTab, myDeliveriesTab);
        setCenter(tabPane);
    }
    
    // Getters
    public TableView<String> getAvailableOrdersTable() {
        return availableOrdersTable;
    }
    
    public TableView<String> getMyDeliveriesTable() {
        return myDeliveriesTable;
    }
} 