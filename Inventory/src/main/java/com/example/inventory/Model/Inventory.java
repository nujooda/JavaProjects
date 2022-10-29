package com.example.inventory.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** Inventory model for parts and products with lists and functions as specified in UML diagram. */
public class Inventory {
    /** A list of all parts. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /** A list of all products. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /** Adds a part. */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    /** Adds a product. */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    /** Looks up a part with an integer. */
    public static Part lookupPart(int partId){
        Part foundPart = null;
        for(Part part : allParts){
            if(part.getId() == (partId)){
                foundPart =part;
            }
        }
        return foundPart;
    }
    /** Looks up a product with an integer. */
    public static Product lookupProduct(int productId){
        Product foundProduct = null;
        for(Product product : allProducts){
            if(product.getId() == (productId)){
                foundProduct =product;
            }
        }
        return foundProduct;
    }
    /** Looks up all parts that contain a string. */
    public static ObservableList<Part> lookupPart(String searchString){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(Part part : allParts){
            if(part.getName().toLowerCase().contains(searchString.toLowerCase())){
                foundParts.add(part);
            }
        }
        return foundParts;
    }
    /** Looks up all products that contain a string. */
    public static ObservableList<Product> lookupProductt(String searchString){
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for(Product product : allProducts){
            if(product.getName().toLowerCase().contains(searchString.toLowerCase())){
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }
    /** Updates a part at an index. */
    public static void updatePart (int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    /** Updates a product at an index. */
    public static void updateProduct (int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }
    /** Deletes a part. */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }
    /** Deletes a product. */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }
    /** Gets all parts. */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /** Gets all products.*/
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}