package com.example.inventorymanagement;

import com.example.inventorymanagement.Models.InHouse;
import com.example.inventorymanagement.Models.Inventory;
import com.example.inventorymanagement.Models.Outsourced;
import com.example.inventorymanagement.Models.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/MainSceen.fxml"));
        primaryStage.setTitle("Inventory Manager");
        primaryStage.setScene(new Scene(root, 1070, 440));
        primaryStage.show();
    }
    
    public static void main(String[] args) {

        Application.launch(OKButton.class, args);
        //Sample in-house parts (genetic modifications)
        Inventory.addPart(new InHouse(1, "Heightened senses", 1500.00, 18, 1, 20, 1));
        Inventory.addPart(new InHouse(2, "Chromatophores", 267300.00, 8, 1, 20, 3));
        Inventory.addPart(new InHouse(3, "Gorilla strength", 125999.00, 12, 1, 20, 3));
        Inventory.addPart(new InHouse(4, "Regenerative healing", 300500.00, 20, 1, 20, 4));
        Inventory.addPart(new InHouse(5, "Leopard claws", 35123.99, 6, 1, 20, 2));
        Inventory.addPart(new InHouse(6, "Wings", 498500.00, 15, 4, 20, 2));
        Inventory.addPart(new InHouse(7, "Venomous bite", 13852.99, 2, 1, 20, 3));
        //Sample outsourced parts (robotic augmentations)
        Inventory.addPart(new Outsourced(8, "Mechanical hand", 84500.00, 4, 1, 20, "Skynet"));
        Inventory.addPart(new Outsourced(9, "Camera eyes", 3000.00, 20, 1, 20, "Skynet"));
        Inventory.addPart(new Outsourced(10, "Nuclear heart", 342999.99, 1, 1, 20, "Stark Industries"));
        Inventory.addPart(new Outsourced(11, "Neural uplink", 1800.00, 18, 1, 20, "Skynet"));
        Inventory.addPart(new Outsourced(12, "Titanium skeleton", 45000.00, 5, 1, 20, "Skynet"));
        Inventory.addPart(new Outsourced(13, "Nanite array", 8499999.99, 1, 1, 20, "Stark Industries"));
        Inventory.addPart(new Outsourced(14, "Long fall boots", 617159.00, 2, 1, 20, "Aperture Science"));

        //Sample products and their related parts
        Inventory.addProduct(new Product(1, "Killer cyborg", 89999.99, 5, 0, 5));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(12));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(9));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(11));
        Inventory.addProduct(new Product(2, "Ultimate predator", 299999.99, 2, 0, 5));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(1));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(3));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(5));
        Inventory.addProduct(new Product(3, "Robo-raptor", 3999999.00, 1, 0, 5));
        Inventory.lookupProduct(3).addAssociatedPart(Inventory.lookupPart(6));
        Inventory.lookupProduct(3).addAssociatedPart(Inventory.lookupPart(1));
        Inventory.lookupProduct(3).addAssociatedPart(Inventory.lookupPart(9));
        Inventory.lookupProduct(3).addAssociatedPart(Inventory.lookupPart(14));
        Inventory.addProduct(new Product(4, "Immortal", 9999999.00, 1, 0, 5));
        Inventory.lookupProduct(4).addAssociatedPart(Inventory.lookupPart(4));
        Inventory.lookupProduct(4).addAssociatedPart(Inventory.lookupPart(10));
        Inventory.lookupProduct(4).addAssociatedPart(Inventory.lookupPart(13));

        launch(args);
    }

}
