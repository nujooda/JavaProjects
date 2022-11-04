package com.example.inventory.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.inventory.Model.Inventory;
import com.example.inventory.Model.Part;
import com.example.inventory.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    public static Part selectedPart;
    public static Part getSelectedPart() {
        return selectedPart;
    }
    public static Product selectedProduct;

    public Button exitButton;

    public TextField partSearchBar;

    public TableView<Part> partTable;

    public TableColumn<Part, Integer> partIdCol;

    public TableColumn<Part, String> partNameCol;

    public TableColumn<Part, Integer> partInventoryCol;

    public TableColumn<Part, Integer> partPriceCol;

    public Button addPartButton;

    public Button modifyPartButton;

    public Button deletePartButton;

    public TextField productSearchBar;

    public TableView<Product> productTable;

    public TableColumn<Product, String> productIdCol;

    public TableColumn<Product, Integer> productNameCol;

    public TableColumn<Product, Integer> productInventoryCol;

    public TableColumn<Product, Integer> productPriceCol;

    public Button addProductButton;

    public Button modifyProductButton;

    public Button deleteProductButton;


    public void exitButtonPressed() {
        System.exit(0);
    }

    private ObservableList<Part> partSearch(String searchString) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(searchString.toLowerCase()) || Integer.toString(part.getId()).contains(searchString)) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    public void partSearched() {
        String searchString = partSearchBar.getText();
        ObservableList<Part> foundParts = partSearch(searchString);
        partTable.setItems(foundParts);
        if (foundParts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No part found");
            alert.setContentText("Please search for something else");
            alert.show();
        }
    }

    private ObservableList<Product> productSearch(String searchString) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();


        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(searchString.toLowerCase()) || Integer.toString(product.getId()).contains(searchString)) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    public void productSearched() {
        String searchString = productSearchBar.getText();
        ObservableList<Product> foundProducts = productSearch(searchString);
        productTable.setItems(foundProducts);
        if (foundProducts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No product found");
            alert.setContentText("Please search for something else");
            alert.show();
        }
    }

    public void addPartButtonPressed() throws IOException {
        //Opens add part menu in a new window
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddPart.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Parts");
        stage.setScene(new Scene(root1));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    public void modifyPartButtonPressed() throws IOException {
        selectedPart = partTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) { //Missing selection error
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No part selected");
            alert.setContentText("Select a part and try again");
            alert.showAndWait();
        } else {
            //Opens add part menu in a new window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyPart.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }
    }

    public void deletePartButtonPressed() {
        selectedPart = partTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) { //Missing selection error
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No part selected");
            alert.setContentText("Select a part and try again");
            alert.showAndWait();
        } else { //Confirmation check
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WARNING!");
            alert.setHeaderText("You are about to delete this part");
            alert.setContentText("Are you sure you want to do this?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart); //Delete!
            }
        }
    }

    public void addProductButtonPressed() throws IOException {
        //Opens add part menu in a new window
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddProduct.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Products");
        stage.setScene(new Scene(root1));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    public void modifyProductButtonPressed() throws IOException {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) { //Missing selection error
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No product selected");
            alert.setContentText("Select a product and try again");
            alert.showAndWait();
        } else {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ModifyProduct.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Modify Products");
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }
    }

    public void deleteProductButtonPressed() {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) { //Missing selection error
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No product selected");
            alert.setContentText("Select a product and try again");
            alert.showAndWait();
        } else { //Confirm to delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WARNING!");
            alert.setHeaderText("You are about to delete this product");
            alert.setContentText("Are you sure you want to do this?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) { //Delete confirmed
                ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
                if (associatedParts.size() > 0) { //Check for associated parts
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setHeaderText("This product has parts associated with it");
                    alert2.setContentText("Modify product and remove parts before deleting");
                    alert2.showAndWait();
                } else { //Delete!
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

