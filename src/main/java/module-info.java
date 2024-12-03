module com.example.food_delivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    // 导出控制器包给JavaFX FXML
    exports com.example.food_delivery.controller;
    exports com.example.food_delivery.controller.customer;
    exports com.example.food_delivery.controller.restaurant;
    exports com.example.food_delivery.controller.delivery;
    
    // 允许FXML访问控制器包
    opens com.example.food_delivery.controller to javafx.fxml;
    opens com.example.food_delivery.controller.customer to javafx.fxml;
    opens com.example.food_delivery.controller.restaurant to javafx.fxml;
    opens com.example.food_delivery.controller.delivery to javafx.fxml;
    
    // 导出主应用程序包
    exports com.example.food_delivery.views;
}