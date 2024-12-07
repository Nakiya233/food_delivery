module com.example.food_delivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    // 打开包以供JavaFX访问
    opens com.example.food_delivery.controller to javafx.fxml;
    opens com.example.food_delivery.controller.restaurant to javafx.fxml;
    opens com.example.food_delivery.controller.customer to javafx.fxml;
    opens com.example.food_delivery.controller.delivery to javafx.fxml;
    opens com.example.food_delivery.model to javafx.base;
    opens com.example.food_delivery.views to javafx.fxml, javafx.graphics;
    opens com.example.food_delivery.views.customer to javafx.fxml, javafx.graphics;
    opens com.example.food_delivery.views.restaurant to javafx.fxml, javafx.graphics;
    opens com.example.food_delivery.views.delivery to javafx.fxml, javafx.graphics;

    exports com.example.food_delivery.controller;
    exports com.example.food_delivery.controller.restaurant;
    exports com.example.food_delivery.controller.customer;
    exports com.example.food_delivery.controller.delivery;
    exports com.example.food_delivery.model;
    exports com.example.food_delivery.views;
    exports com.example.food_delivery.views.customer;
    exports com.example.food_delivery.views.restaurant;
    exports com.example.food_delivery.views.delivery;
}