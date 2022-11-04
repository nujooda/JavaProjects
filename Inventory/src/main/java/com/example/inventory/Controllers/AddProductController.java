package com.example.inventory.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.example.inventory.Model.Inventory;
import com.example.inventory.Model.Part;
import com.example.inventory.Model.Product;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.inventory.Model.Inventory.lookupProduct;

public class AddProductController implements Initializable{

    public TextField partSearchBar;

    public TextField productIdField;

    public TextField productNameField;

    public TextField productInventoryField;

    public TextField productPriceField;

    public TextField productMaxField;

    public TextField productMinField;

    public Label recommendedPriceText;

    public Button addPartButton;

    public Button removePartButton;

    public Button productSaveExitButton;

    public Button productSaveButton;

    public Button productCancelButton;

    public TableView<Part> partTable;

    public TableColumn<Part, Integer> partIdCol;

    public TableColumn<Part, String> partNameCol;

    public TableColumn<Part, Integer> partInventoryCol;

    public TableColumn<Part, Integer> partPriceCol;

    public TableView<Part> associatedPartTable;

    public TableColumn<Part, Integer> associatedPartIdCol;

    public TableColumn<Part, String> associatedPartNameCol;

    public TableColumn<Part, Integer> associatedPartInventoryCol;

    public TableColumn<Part, Integer> associatedPartPriceCol;

    private static Part partToMove;

    public boolean success;

    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();



    private ObservableList<Part> partSearch(String searchString){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part part : allParts){
            if(part.getName().toLowerCase().contains(searchString.toLowerCase()) || Integer.toString(part.getId()).contains(searchString)){
                foundParts.add(part);
            }
        }
        return foundParts;
    }

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

    public void addPartButtonPressed(ActionEvent actionEvent) {
        partToMove = partTable.getSelectionModel().getSelectedItem();
        if (partToMove == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No part selected");
            alert.setContentText("Select a part and try again");
            alert.showAndWait();
        }
        else{
            associatedParts.add(partToMove);
            associatedPartTable.setItems(associatedParts);
        }
    }

    public void removePartButtonPressed(ActionEvent actionEvent) {
        partToMove = associatedPartTable.getSelectionModel().getSelectedItem();
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
                associatedPartTable.setItems(associatedParts);
            }
        }
    }

    public static int getNewID() {
        int newID = 1;
        while ( lookupProduct(newID) != null){
            newID++;
        }
        return newID;
    }

    private void saveData() {
        try {
            success = false; //prevents save button actions from executing unless success is achieved
            //Empty field error
            if(productNameField.getText() == "" || productPriceField.getText() == "" || productInventoryField.getText() == "" || productMinField.getText() == "" || productMaxField.getText() == ""){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Value Error");
                alert.setHeaderText("One or more fields appear to be empty");
                alert.setContentText("Make sure all fields are filled in appropriately");
                alert.showAndWait();
            }
            else if (Integer.parseInt(productMinField.getText()) < 0) { //Negative minimum error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setHeaderText("Error in minimum field");
                alert.setContentText("Minimum cannot be negative");
                alert.showAndWait();
            }
            else if (Integer.parseInt(productMinField.getText()) > Integer.parseInt(productMaxField.getText())) { //Min and Max reversed error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setHeaderText("Error in either minimum or maximum field");
                alert.setContentText("Maximum cannot be less than minimum");
                alert.showAndWait();
            }
            else if (Integer.parseInt(productMinField.getText()) > Integer.parseInt(productInventoryField.getText()) ||
                    Integer.parseInt(productMaxField.getText()) < Integer.parseInt(productInventoryField.getText())) { //Inventory out of bounds error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setHeaderText("Error in inventory field");
                alert.setContentText("Inventory cannot be outside the range of minimum to maximum");
                alert.showAndWait();
            }
            else {//No errors
                int id = getNewID();
                String name = productNameField.getText();
                double price = Double.parseDouble(productPriceField.getText());
                int inventory = Integer.parseInt(productInventoryField.getText());
                int min = Integer.parseInt(productMinField.getText());
                int max = Integer.parseInt(productMaxField.getText());
                success = true; //Success!
                Inventory.addProduct(new Product(id, name, price, inventory, min, max));
                for (Part part : associatedParts) {
                    Inventory.lookupProduct(id).addAssociatedPart(part);
                }
            }
        } catch (NumberFormatException e) { //Generic input error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("One or more fields are having trouble");
            alert.setContentText("Check fields for correct input");
            alert.showAndWait();
        }
    }

    public void productSaveButtonPressed(ActionEvent actionEvent) {
        saveData();
        if(success){
            productIdField.clear();
            productNameField.clear();
            productPriceField.clear();
            productInventoryField.clear();
            productMinField.setText("0");
            productMaxField.clear();
            productNameField.requestFocus();
            associatedPartTable.getItems().clear();
            recommendedPriceText.setText("$$$");
        }
    }

    public void productSaveExitButtonPressed(ActionEvent actionEvent) {
        saveData();
        if(success){
            Stage stage = (Stage) productCancelButton.getScene().getWindow();
            stage.close();
        }
    }

    public void productCancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) productCancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
