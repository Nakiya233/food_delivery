module com.example.food_delivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    // 打开包以供JavaFX访问
    opens com.example.food_delivery.controller to javafx.fxml;
    opens com.example.food_delivery.controller.restaurant to javafx.fxml;
    opens com.example.food_delivery.model to javafx.base;
    opens com.example.food_delivery.views to javafx.fxml, javafx.graphics;
    

    exports com.example.food_delivery.controller;
    exports com.example.food_delivery.controller.restaurant;
    exports com.example.food_delivery.model;
    exports com.example.food_delivery.views;
}