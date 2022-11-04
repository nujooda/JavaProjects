package com.example.inventory.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.inventory.Model.InHouse;
import com.example.inventory.Model.Inventory;
import com.example.inventory.Model.Outsourced;
import com.example.inventory.Model.Part;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    private boolean inHouseBool = true;

    public RadioButton inHouseButton;

    public RadioButton outsourcedButton;

    public ToggleGroup PartSwitcher;

    public TextField partIdField;

    public TextField partNameField;

    public TextField partInventoryField;

    public TextField partPriceField;

    public TextField partMaxField;

    public TextField partMinField;

    public TextField machineCompanyField;

    public Label machineCompanyLabel;

    public Button partSaveButton;

    public Button partCancelButton;


    public void inHouseButtonPressed(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Machine ID");
        inHouseBool = true;
        machineCompanyField.setPromptText("Enter machine ID #");
    }

    public void outsourcedButtonPressed(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Company Name");
        inHouseBool = false;
        machineCompanyField.setPromptText("Enter vendor name");
    }

    public void partSaveButtonPressed(ActionEvent actionEvent) {
        try {
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
            else{  //No errors
                int id = Integer.parseInt(partIdField.getText());
                String name = partNameField.getText();
                double price = Double.parseDouble(partPriceField.getText());
                int inventory = Integer.parseInt(partInventoryField.getText());
                int min = Integer.parseInt(partMinField.getText());
                int max = Integer.parseInt(partMaxField.getText());
                if (inHouseBool) { //In house selected
                    int machine = Integer.parseInt(machineCompanyField.getText());
                    InHouse update = (new InHouse(id, name, price, inventory, min, max, machine));
                    Inventory.updatePart(id-1, update);
                }
                else { //Outsourced selected
                    String company = machineCompanyField.getText();
                    Outsourced update = (new Outsourced(id, name, price, inventory, min, max, company));
                    Inventory.updatePart(id-1, update);
                }
                Stage stage = (Stage) partCancelButton.getScene().getWindow();
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

    public void partCancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) partCancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Part selectedPart = MainScreenController.getSelectedPart();
        if(selectedPart instanceof InHouse){
            inHouseButton.setSelected(true);
            machineCompanyLabel.setText("Machine ID");
            inHouseBool = true;
            machineCompanyField.setPromptText("Enter machine ID #");
            machineCompanyField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        if(selectedPart instanceof Outsourced){
            outsourcedButton.setSelected(true);
            machineCompanyLabel.setText("Company Name");
            inHouseBool = false;
            machineCompanyField.setPromptText("Enter vendor name");
            machineCompanyField.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
        }
        partIdField.setText(String.valueOf(selectedPart.getId()));
        partNameField.setText(selectedPart.getName());
        partInventoryField.setText(String.valueOf(selectedPart.getStock()));
        partPriceField.setText(String.valueOf(selectedPart.getPrice()));
        partMaxField.setText(String.valueOf(selectedPart.getMax()));
        partMinField.setText(String.valueOf(selectedPart.getMin()));
    }
}

