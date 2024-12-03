package com.example.food_delivery.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginView extends VBox {
    
    private TextField usernameField;
    private PasswordField passwordField;
    private ComboBox<String> userTypeComboBox;
    private Button loginButton;
    private Button registerButton;
    
    public LoginView() {
        initializeComponents();
        setupLayout();
        setupStyles();
    }
    
    private void initializeComponents() {
        usernameField = new TextField();
        usernameField.setPromptText("请输入用户名");
        
        passwordField = new PasswordField();
        passwordField.setPromptText("请输入密码");
        
        userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll("顾客", "商家", "配送员");
        userTypeComboBox.setPromptText("选择用户类型");
        
        loginButton = new Button("登录");
        registerButton = new Button("注册");
    }
    
    private void setupLayout() {
        setAlignment(Pos.CENTER);
        setSpacing(20);
        setPadding(new Insets(20));
        
        Text title = new Text("外卖配送系统");
        title.setStyle("-fx-font-size: 24px;");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        
        grid.add(new Label("用户名:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("密码:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(new Label("用户类型:"), 0, 2);
        grid.add(userTypeComboBox, 1, 2);
        
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton, registerButton);
        
        getChildren().addAll(title, grid, buttonBox);
    }
    
    private void setupStyles() {
        loginButton.setStyle("-fx-base: #4CAF50;");
        setStyle("-fx-background-color: white;");
    }
    
    // Getters for the components
    public TextField getUsernameField() {
        return usernameField;
    }
    
    public PasswordField getPasswordField() {
        return passwordField;
    }
    
    public ComboBox<String> getUserTypeComboBox() {
        return userTypeComboBox;
    }
    
    public Button getLoginButton() {
        return loginButton;
    }
    
    public Button getRegisterButton() {
        return registerButton;
    }
} 