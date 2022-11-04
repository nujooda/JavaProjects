package com.example.inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.inventory.Model.*;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
        primaryStage.setTitle("Inventory");
        primaryStage.setScene(new Scene(root, 1070,440));
        primaryStage.show();

        // Sample Parts
        Inventory.addPart(new InHouse(1,"Part_1", 10.00, 10, 1, 10, 101));
        Inventory.addPart(new InHouse(2,"Part_2", 12.99, 10, 1, 10, 102));
        Inventory.addPart(new InHouse(3,"Part_3", 5.99, 10, 1, 10, 103));

        //Sample outsourced parts (robotic augmentations)
        Inventory.addPart(new Outsourced(8,"Part_4", 20.99, 10, 1, 10, "Company_1"));
        Inventory.addPart(new Outsourced(9,"Part_5", 50.00, 10, 1, 10, "Company_2"));
        Inventory.addPart(new Outsourced(10,"Part_6", 11.99, 10, 1, 10, "Company_3"));

        //Sample Products
        Inventory.addProduct(new Product(1, "Product_1",300.00, 10, 1, 10));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(1));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(2));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(3));
        Inventory.addProduct(new Product(2, "Product_2",1000.99, 10, 1, 10));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(8));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(9));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(10));
        Inventory.addProduct(new Product(3, "Product_3",400.00, 10, 1, 10));
        Inventory.lookupProduct(3).addAssociatedPart(Inventory.lookupPart(2));
        Inventory.lookupProduct(3).addAssociatedPart(Inventory.lookupPart(9));

    }

    public static void main(String[] args) {
        launch(args);
    }
}