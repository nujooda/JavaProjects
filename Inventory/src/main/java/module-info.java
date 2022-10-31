module com.example.inventory {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.inventory to javafx.fxml;
    exports com.example.inventory;
    exports com.example.inventory.Controllers;
    exports com.example.inventory.Model;

}