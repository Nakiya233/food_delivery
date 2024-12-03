package com.example.food_delivery.views.restaurant;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class RestaurantMainView extends BorderPane {
    
    private TableView<String> pendingOrdersTable;
    private Label todayOrdersLabel;
    private Label todayRevenueLabel;
    private Label pendingOrdersLabel;
    
    public RestaurantMainView() {
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        // 初始化组件
        pendingOrdersTable = new TableView<>();
        todayOrdersLabel = new Label("0");
        todayRevenueLabel = new Label("¥0.00");
        pendingOrdersLabel = new Label("0");
        
        // 创建菜单栏
        MenuBar menuBar = new MenuBar();
        Menu menuMenu = new Menu("菜单");
        Menu orderMenu = new Menu("订单");
        Menu statsMenu = new Menu("统计");
        Menu accountMenu = new Menu("账户");
        
        MenuItem menuManagementItem = new MenuItem("菜单管理");
        MenuItem orderManagementItem = new MenuItem("订单管理");
        MenuItem salesStatisticsItem = new MenuItem("销售统计");
        MenuItem profileItem = new MenuItem("商家信息");
        MenuItem logoutItem = new MenuItem("退出登录");
        
        menuMenu.getItems().add(menuManagementItem);
        orderMenu.getItems().add(orderManagementItem);
        statsMenu.getItems().add(salesStatisticsItem);
        accountMenu.getItems().addAll(profileItem, logoutItem);
        
        menuBar.getMenus().addAll(menuMenu, orderMenu, statsMenu, accountMenu);
        setTop(menuBar);
    }
    
    private void setupLayout() {
        // 设置中心区域
        TabPane tabPane = new TabPane();
        
        // 待处理订单标签页
        Tab pendingOrdersTab = new Tab("待处理订单");
        pendingOrdersTab.setClosable(false);
        VBox pendingOrdersBox = new VBox(10);
        pendingOrdersBox.setPadding(new Insets(10));
        pendingOrdersBox.getChildren().add(pendingOrdersTable);
        pendingOrdersTab.setContent(pendingOrdersBox);
        
        // 今日概览标签页
        Tab overviewTab = new Tab("今日概览");
        overviewTab.setClosable(false);
        GridPane overviewGrid = new GridPane();
        overviewGrid.setHgap(20);
        overviewGrid.setVgap(20);
        overviewGrid.setPadding(new Insets(10));
        
        overviewGrid.add(new Label("今日订单数:"), 0, 0);
        overviewGrid.add(todayOrdersLabel, 1, 0);
        overviewGrid.add(new Label("今日营业额:"), 0, 1);
        overviewGrid.add(todayRevenueLabel, 1, 1);
        overviewGrid.add(new Label("待处理订单:"), 0, 2);
        overviewGrid.add(pendingOrdersLabel, 1, 2);
        
        overviewTab.setContent(overviewGrid);
        
        tabPane.getTabs().addAll(pendingOrdersTab, overviewTab);
        setCenter(tabPane);
    }
    
    // Getters
    public TableView<String> getPendingOrdersTable() {
        return pendingOrdersTable;
    }
    
    public Label getTodayOrdersLabel() {
        return todayOrdersLabel;
    }
    
    public Label getTodayRevenueLabel() {
        return todayRevenueLabel;
    }
    
    public Label getPendingOrdersLabel() {
        return pendingOrdersLabel;
    }
} 