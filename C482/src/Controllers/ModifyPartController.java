package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.net.URL;
import java.util.ResourceBundle;
/** Controller for modify part window with LOGICAL ERROR. */
public class ModifyPartController implements Initializable {
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
    /** LOGICAL RUNTIME ERROR This was where my logical error was. When I saved, the product I was trying to change stayed the same, and a copy of it with the changes would appear in the table of parts.
     * After trying it a few times I realized that it wasn't putting a copy into the part table, it was overwriting a different record with the changes I was trying to make to the selected part.
     * I forgot to subtract 1 from the ID because arrays begin at 0. I have now corrected the math so that the selected part is being updated. I am hoping that there aren't future issues associated with deleting parts,
     * because my program uses the lowest available ID# and I do not know if the ID# and part index are linked. I am concerned that if one part is deleted and then a subsequent part is added, taking the ID# of the deleted part,
     * but has a different index, then my method of updating will need a rework. UPDATE: All seems to work without incident now that I have added the ability to delete parts and have tested for this problem.. */
    public Button partSaveButton;
    /** Close window button. */
    public Button partCancelButton;

    /** Sets part to in-house and updates the field and label for machine id. */
    public void inHouseButtonPressed(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Machine ID");
        inHouseBool = true;
        machineCompanyField.setPromptText("Enter machine ID #");
    }
    /** Sets part to outsourced and updates the field and label for company name. */
    public void outsourcedButtonPressed(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Company Name");
        inHouseBool = false;
        machineCompanyField.setPromptText("Enter vendor name");
    }
    /** Function that saves data and closes window, returning to main menu.
     * This was where my logical error was. When I saved, the product I was trying to change stayed the same, and a copy of it with the changes would appear in the table of parts.
     * After trying it a few times I realized that it wasn't putting a copy into the part table, it was overwriting a different record with the changes I was trying to make to the selected part.
     * I forgot to subtract 1 from the ID because arrays begin at 0. I have now corrected the math so that the selected part is being updated. I am hoping that there aren't future issues associated with deleting parts,
     * because my program uses the lowest available ID# and I do not know if the ID# and part index are linked. I am concerned that if one part is deleted and then a subsequent part is added, taking the ID# of the deleted part,
     * but has a different index, then my method of updating will need a rework. UPDATE: All seems to work without incident now that I have added the ability to delete parts and have tested for this problem.
     */
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
    /** Closes window and returns to main menu. */
    public void partCancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) partCancelButton.getScene().getWindow();
        stage.close();
    }
    /** When window is initialized, all text fields are filled with the existing values of the selected part that can be changed, and the label and radio buttons are set for either in house or outsourced. */
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

