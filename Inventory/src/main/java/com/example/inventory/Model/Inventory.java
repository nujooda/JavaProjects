package com.example.inventory.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** Inventory model for parts and products with lists and functions as specified in UML diagram. */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId){
        Part foundPart = null;
        for(Part part : allParts){
            if(part.getId() == (partId)){
                foundPart =part;
            }
        }
        return foundPart;
    }

    public static Product lookupProduct(int productId){
        Product foundProduct = null;
        for(Product product : allProducts){
            if(product.getId() == (productId)){
                foundProduct =product;
            }
        }
        return foundProduct;
    }

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

    public static void updatePart (int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct (int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}