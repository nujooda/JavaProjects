package Controllers;

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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for the main screen of the application. An update I would like to make if this application was expanded would be to create a window opener function that all the add and modify buttons could call.
 */
public class MainScreenController implements Initializable {
    /** Used to remember a selected part to modify.
     */
    public static Part selectedPart;
    /** Returns the selected part to modify.
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }
    /** Used to remember a selected product to modify.
     */
    public static Product selectedProduct;
    /** Exit button. */
    public Button exitButton;
    /** Search bar for parts. */
    public TextField partSearchBar;
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
    /** Add part button */
    public Button addPartButton;
    /** Modify part button. */
    public Button modifyPartButton;
    /** Delete part button. */
    public Button deletePartButton;
    /** Search bar for products. */
    public TextField productSearchBar;
    /** Product table. */
    public TableView<Product> productTable;
    /** Product table id column. */
    public TableColumn<Product, String> productIdCol;
    /** Product table name column. */
    public TableColumn<Product, Integer> productNameCol;
    /** Product table inventory column. */
    public TableColumn<Product, Integer> productInventoryCol;
    /** Product table price column. */
    public TableColumn<Product, Integer> productPriceCol;
    /** Add product button. */
    public Button addProductButton;
    /** Modify product button. */
    public Button modifyProductButton;
    /** Delete product button. */
    public Button deleteProductButton;

    /** Closes application when exit button is pressed.
     */
    public void exitButtonPressed() {
        System.exit(0);
    }
    /** Search function for parts.
     */
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
    /** Calls search function whenever something is typed in the part search bar, then updates the part table. Runs after any key is released while focused on search bar.
     */
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
    /** Search function for products.
     */
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
    /** Calls search function whenever something is typed in the product search bar, then updates the product table. Runs after any key is released while focused on search bar.
     */
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
    /**Opens add part menu, contains the code necessary to have the part menu replace the main screen instead of opening on top if needed.
     */
    public void addPartButtonPressed() throws IOException {
        //Opens add part menu in a new window
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddPartScreen.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Parts");
        stage.setScene(new Scene(root1));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        //This code can be used instead to make the add part menu replace the main menu
        /*Parent root= FXMLLoader.load(getClass().getResource("/view/AddPartScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 470,330);
        stage.setTitle("Add Parts");
        stage.setScene(scene);
        stage.show();*/

        //IMPORTANT!!! If above code is used to make new window replace main, this code must be used to exit or cancel back to main from them!!!!
        /*  Parent root= FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1070,440);
            stage.setTitle("Inventory Manager");
            stage.setScene(scene);
            stage.show();
         */
    }
    /**Opens modify part menu if a part is selected, contains the code necessary to have the part menu replace the main screen instead of opening on top if needed.
     */
    public void modifyPartButtonPressed() throws IOException {
        selectedPart = partTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) { //Missing selection error
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No part selected");
            alert.setContentText("Select a part and try again");
            alert.showAndWait();
        } else {
            //Opens add part menu in a new window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ModifyPartScreen.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            //This code can be used instead to make the modify part menu replace the main menu
            /*Parent root= FXMLLoader.load(getClass().getResource("/view/ModifyPartScreen.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 470,330);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
            */

            //IMPORTANT!!! If above code is used to make new window replace main, this code must be used to exit or cancel back to main from them!!!!
            /*Parent root= FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1070,440);
            stage.setTitle("Inventory Manager");
            stage.setScene(scene);
            stage.show();
            */
        }
    }
    /**
     * Deletes selected part on button press, alerts if no part is selected and asks for confirmation before deleting.
     */
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
    /**Opens add product menu, contains the code necessary to have the product menu replace the main screen instead of opening on top if needed.
     */
    public void addProductButtonPressed() throws IOException {
        //Opens add part menu in a new window
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddProductScreen.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Products");
        stage.setScene(new Scene(root1));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        //This code can be used instead to make the add part menu replace the main menu
        /*Parent root= FXMLLoader.load(getClass().getResource("/view/AddPartScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 890,600);
        stage.setTitle("Add Parts");
        stage.setScene(scene);
        stage.show();*/

        //IMPORTANT!!! If above code is used to make new window replace main, this code must be used to exit or cancel back to main from them!!!!
        /*  Parent root= FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1070,440);
            stage.setTitle("Inventory Manager");
            stage.setScene(scene);
            stage.show();
         */
    }
    /**Opens modify product menu if a product is selected, contains the code necessary to have the product menu replace the main screen instead of opening on top if needed.
     */
    public void modifyProductButtonPressed() throws IOException {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) { //Missing selection error
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No product selected");
            alert.setContentText("Select a product and try again");
            alert.showAndWait();
        } else {
            //Opens add part menu in a new window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ModifyProductScreen.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Modify Products");
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            //This code can be used instead to make the add part menu replace the main menu
        /*Parent root= FXMLLoader.load(getClass().getResource("/view/AddPartScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 890,600);
        stage.setTitle("Modify Parts");
        stage.setScene(scene);
        stage.show();*/

            //IMPORTANT!!! If above code is used to make new window replace main, this code must be used to exit or cancel back to main from them!!!!
        /*  Parent root= FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1070,440);
            stage.setTitle("Inventory Manager");
            stage.setScene(scene);
            stage.show();
         */
        }
    }
    /**
     * Deletes selected product on button press, alerts if no product is selected and asks for confirmation before deleting. Does not work if the selected product has parts associated with it.
     */
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
    /** Part table and product table populated for first time when window is initialized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populates parts table
        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        //Populates product table
        productTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

