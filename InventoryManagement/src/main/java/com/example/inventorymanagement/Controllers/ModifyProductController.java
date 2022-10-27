package com.example.inventorymanagement.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.example.inventorymanagement.Models.Inventory;
import com.example.inventorymanagement.Models.Part;
import com.example.inventorymanagement.Models.Product;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** Controller for modify product window. */
public class ModifyProductController  implements Initializable {
    /** Search bar for part list. */
    public TextField partSearchBar;
    /** Product ID field, IDs are auto generated, so this is disabled. */
    public TextField productIdField;
    /** Product name field. */
    public TextField productNameField;
    /** Product inventory field. */
    public TextField productInventoryField;
    /**Product price field */
    public TextField productPriceField;
    /** Product max field. */
    public TextField productMaxField;
    /** Product min field. */
    public TextField productMinField;
    /** Label that is updated when parts are associated with a product and displays a recommended price equal to double the value of all associated parts. */
    public Label recommendedPriceText;
    /** Button that adds a selected part from the list of all parts to the list of associated parts. */
    public Button addPartButton;
    /** Button that removes a selected part from the list of associated parts. */
    public Button removePartButton;
    /** Button that saves and exits the window. */
    public Button productSaveButton;
    /** Button that closes the window without saving. */
    public Button productCancelButton;
    /** Part table. */
    public TableView<Part> partTable;
    /** Part table id column. */
    public TableColumn<Part, Integer> partIdCol;
    /** Part table name column. */
    public TableColumn<Part, String> partNameCol;
    /** Part table inventory column. */
    public TableColumn<Part, Integer> partInventoryCol;
    /** Part table price column. */
    public TableColumn<Part, Integer> partPriceCol;
    /** Associated part table. */
    public TableView<Part> associatedPartTable;
    /** Associated part table id column. */
    public TableColumn<Part, Integer> associatedPartIdCol;
    /** Associated part table name column. */
    public TableColumn<Part, String> associatedPartNameCol;
    /** Associated part table inventory column. */
    public TableColumn<Part, Integer> associatedPartInventoryCol;
    /** Associated part table price column. */
    public TableColumn<Part, Integer> associatedPartPriceCol;
    /** Part that is going to be moved from one table to another. */
    private static Part partToMove;
    /** List of all parts in the associated parts table. */
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /** Function that calculates double the price of all associated parts of a product and sets the label recommended price to that value. */

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
    /** Calls search function whenever something is typed in the part search bar, then updates the part table. Runs after any key is released while focused on search bar. */
    public void partSearched() {
        //Calls search function for parts
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
    /** Adds selected part in part table to associated part table. */
    public void addPartButtonPressed(ActionEvent actionEvent) {
        partToMove = partTable.getSelectionModel().getSelectedItem();
        if (partToMove == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No part selected");
            alert.setContentText("Select a part and try again");
            alert.showAndWait();
        } else {
            associatedParts.add(partToMove);
            associatedPartTable.setItems(associatedParts);
        }
    }
    /** Removes selected part from associated part table, but requires confirmation. */
    public void removePartButtonPressed(ActionEvent actionEvent) {
        partToMove =associatedPartTable.getSelectionModel().getSelectedItem();
        if (partToMove == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No part selected");
            alert.setContentText("Select a part and try again");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WARNING!");
            alert.setHeaderText("You are about to remove this part");
            alert.setContentText("Are you sure you want to do this?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(partToMove);
            }
        }
    }
    /** Error checks, then saves data and closes window. */
    public void productSaveButtonPressed(ActionEvent actionEvent) {
        try {
            //Empty field error
            if (productNameField.getText() == "" || productPriceField.getText() == "" || productInventoryField.getText() == "" || productMinField.getText() == "" || productMaxField.getText() == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Value Error");
                alert.setHeaderText("One or more fields appear to be empty");
                alert.setContentText("Make sure all fields are filled in appropriately");
                alert.showAndWait();
            } else if (Integer.parseInt(productMinField.getText()) < 0) { //Negative minimum error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setHeaderText("Error in minimum field");
                alert.setContentText("Minimum cannot be negative");
                alert.showAndWait();
            } else if (Integer.parseInt(productMinField.getText()) > Integer.parseInt(productMaxField.getText())) { //Min and Max reversed error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setHeaderText("Error in either minimum or maximum field");
                alert.setContentText("Maximum cannot be less than minimum");
                alert.showAndWait();
            } else if (Integer.parseInt(productMinField.getText()) > Integer.parseInt(productInventoryField.getText()) ||
                    Integer.parseInt(productMaxField.getText()) < Integer.parseInt(productInventoryField.getText())) { //Inventory out of bounds error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setHeaderText("Error in inventory field");
                alert.setContentText("Inventory cannot be outside the range of minimum to maximum");
                alert.showAndWait();
            } else {//No errors
                int id = Integer.parseInt(productIdField.getText());
                String name = productNameField.getText();
                double price = Double.parseDouble(productPriceField.getText());
                int inventory = Integer.parseInt(productInventoryField.getText());
                int min = Integer.parseInt(productMinField.getText());
                int max = Integer.parseInt(productMaxField.getText());
                Product update = new Product(id, name, price, inventory, min, max);
                Inventory.updateProduct(id - 1, update);
                for (Part part : associatedParts) {
                    Inventory.lookupProduct(id).addAssociatedPart(part);
                }
                Stage stage = (Stage) productCancelButton.getScene().getWindow();
                stage.close();
            }
        } catch (NumberFormatException e) { //Generic input error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("One or more fields are having trouble");
            alert.setContentText("Check fields for correct input");
            alert.showAndWait();
        }
    }
    /** Closes window. */
    public void productCancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) productCancelButton.getScene().getWindow();
        stage.close();
    }
    /** Populates part table with all parts, text fields with values of the selected product, and associated parts table with the products associated parts when window is initialized. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Product selectedProduct = MainScreenController.selectedProduct;
        productIdField.setText(String.valueOf(selectedProduct.getId()));
        productNameField.setText(selectedProduct.getName());
        productInventoryField.setText(String.valueOf(selectedProduct.getStock()));
        productPriceField.setText(String.valueOf(selectedProduct.getPrice()));
        productMaxField.setText(String.valueOf(selectedProduct.getMax()));
        productMinField.setText(String.valueOf(selectedProduct.getMin()));

        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedParts = selectedProduct.getAllAssociatedParts();

        associatedPartTable.setItems(associatedParts);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        //updateRecommendedPrice();
    }
}
