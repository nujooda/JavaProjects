package com.example.inventory.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import com.example.inventory.Model.InHouse;
import com.example.inventory.Model.Inventory;
import com.example.inventory.Model.Outsourced;

import static com.example.inventory.Model.Inventory.lookupPart;

/** Controller for add part window. */
public class AddPartController {

    /** Keeps track of if the part is in house or outsourced. */
    private boolean inHouseBool = true;
    /** Button to set part to in house. */
    public RadioButton inHouseButton;
    /** Button to set part as outsourced. */
    public RadioButton outsourcedButton;
    /** Toggles in house/outsourced radio buttons off when other is selected. */
    public ToggleGroup PartSwitcher;
    /** ID field for part, disabled, IDs are auto generated. */
    public TextField partIdField;
    /** Name field for part. */
    public TextField partNameField;
    /** inventory field for part. */
    public TextField partInventoryField;
    /** price field for part. */
    public TextField partPriceField;
    /** Max stock field for part. */
    public TextField partMaxField;
    /** Min stock field for part, defaults to 0. */
    public TextField partMinField;
    /** Field for either the machine id or company name depending on if part is in house or outsourced. */
    public TextField machineCompanyField;
    /** Label for either the machine id or company name depending on if part is in house or outsourced. */
    public Label machineCompanyLabel;
    /** Save and add another part button. */
    public Button partSaveButton;
    /** Save and close window button. */
    public Button partSaveExitButton;
    /** Close window button. */
    public Button partCancelButton;
    /** Can be set if something was successful. */
    public boolean success;

    /** Sets add part to in-house and updates the field and label for machine id. */
    public void inHouseButtonPressed(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Machine ID");
        inHouseBool = true;
        machineCompanyField.setPromptText("Enter machine ID #");
    }
    /** Sets add part to outsourced and updates the field and label for company name. */
    public void outsourcedButtonPressed(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Company Name");
        inHouseBool = false;
        machineCompanyField.setPromptText("Enter vendor name");
    }
    /** Generates ID numbers and finds the lowest available ID number to use. */
    public static int getNewID() {
        int newID = 1;
        while ( lookupPart(newID) != null){
            newID++;
        }
        return newID;
    }
    /** Function that saves data and sets success to true if it was successful. I added this function instead of putting it all in the save button events because I added a second save method and did not want to duplicate the code. */
    private void saveData() {
        try {
            success = false; //prevents save button actions from executing unless success is achieved
            //Empty field error
            if(partNameField.getText() == "" || partPriceField.getText() == "" || partInventoryField.getText() == "" || partMinField.getText() == "" || partMaxField.getText() == "" || machineCompanyField.getText() == ""){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Value Error");
                alert.setHeaderText("One or more fields appear to be empty");
                alert.setContentText("Make sure all fields are filled in appropriately");
                alert.showAndWait();
            }
            else if (Integer.parseInt(partMinField.getText()) < 0) { //Negative minimum error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setHeaderText("Error in minimum field");
                alert.setContentText("Minimum cannot be negative");
                alert.showAndWait();
            }
            else if (Integer.parseInt(partMinField.getText()) > Integer.parseInt(partMaxField.getText())) { //Min and Max reversed error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setHeaderText("Error in either minimum or maximum field");
                alert.setContentText("Maximum cannot be less than minimum");
                alert.showAndWait();
            }
            else if (Integer.parseInt(partMinField.getText()) > Integer.parseInt(partInventoryField.getText()) ||
                    Integer.parseInt(partMaxField.getText()) < Integer.parseInt(partInventoryField.getText())) { //Inventory out of bounds error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setHeaderText("Error in inventory field");
                alert.setContentText("Inventory cannot be outside the range of minimum to maximum");
                alert.showAndWait();
            }
            else {//No errors
                int id = getNewID();
                String name = partNameField.getText();
                double price = Double.parseDouble(partPriceField.getText());
                int inventory = Integer.parseInt(partInventoryField.getText());
                int min = Integer.parseInt(partMinField.getText());
                int max = Integer.parseInt(partMaxField.getText());
                success = true; //Success!
                if (inHouseBool) { //In house selected
                    int machine = Integer.parseInt(machineCompanyField.getText());
                    Inventory.addPart(new InHouse(id, name, price, inventory, min, max, machine));
                }
                else { //Outsourced selected
                    String company = machineCompanyField.getText();
                    Inventory.addPart(new Outsourced(id, name, price, inventory, min, max, company));
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
    /** Saves data and then clears fields and selects part name so another part can be added easily. */
    public void partSaveButtonPressed(ActionEvent actionEvent) {
        //Saves data
        saveData();
        //Clears fields and focuses on name field again if save was successful
        if(success) {
            partIdField.clear();
            partNameField.clear();
            partInventoryField.clear();
            partPriceField.clear();
            partMaxField.clear();
            partMinField.setText("0");
            machineCompanyField.clear();
            partNameField.requestFocus();
        }
    }
    /** Saves data and then closes window, functions as the single save button from the example would have. */
    public void partSaveExitButtonPressed(ActionEvent actionEvent) {
        //Saves data
        saveData();
        //Exits the window if save was successful
        if (success) {
            Stage stage = (Stage) partCancelButton.getScene().getWindow();
            stage.close();
        }
    }
    /** Exits the window. */
    public void partCancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) partCancelButton.getScene().getWindow();
        stage.close();
    }
}
