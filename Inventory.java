package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 11/11/2020
 * Inventory class is where data on inventory is held and also has methods that modify it.
 */
public class Inventory {

public static ObservableList <Part> partList = FXCollections.observableArrayList();
public static ObservableList<Product> productList = FXCollections.observableArrayList();
public static ObservableList <Part> tempPartList = FXCollections.observableArrayList();
public static ObservableList<Product> tempProductList = FXCollections.observableArrayList();
public static ArrayList<Integer> idList = new ArrayList<Integer>();
private static Product updatingProduct;
private static Part updatingPart;



    /**
     *
     * @return creates an id by adding 1 to array list size, then adds to same list for unique id.
     */
    public static int createId(){

        int id = idList.size() +1;
        idList.add(id);
        return id;
    }

    /**
     *
     * @param product can be stored with this method
     */
    public static void storeUpdatingProduct(Product product) {

        updatingProduct = product;
    }

    /**
     *
     * @return returns stored product.
     */
    public static Product getUpdatingProduct(){

        return updatingProduct;
    }

    /**
     *
     * @return returns stored part object.
     */
    public static Part getUpdatingPart(){

        return updatingPart;
    }

    /**
     *
     * @param part part object is passed here with this method
     */
    public static void storeUpdatingPart(Part part) {

        updatingPart = part;
    }


    /**
     *
     * @param part is added to list
     */
    public static void addPart(Part part){

        partList.add(part);


    }


    /**
     *
     * @param selectedPart
     */
    public static void deletePart(Part selectedPart){

        partList.remove(selectedPart);

    }

    /**
     *
     * @param part, temp part is added to temp list
     */
    public static void addTempPart(Part part){

        tempPartList.add(part);


    }

    /**
     *
     * @param selectedPart, temp part deleted from temp list
     */
    public static void deleteTempPart(Part selectedPart){

        tempPartList.remove(selectedPart);

    }

    /**
     * clearTempPartList ysed to clear temp part list after uses.
     */
    public static void clearTempPartList() {

        tempPartList.clear();
    }

    /**
     *
     * @param product added to list
     */
    public static void addProduct(Product product){

        productList.add(product);

    }

    /**
     *
     * @param selectedProduct removed from list
     *
     */
    public static void deleteProduct(Product selectedProduct){

        productList.remove((selectedProduct));

    }

    /**
     *
     * @param product, temp product added to list
     */
    public static void addTempProduct(Product product){

        tempProductList.add(product);

    }

    /**
     *
     * @param selectedProduct, temp product removed from temp list
     *
     */
    public static void deleteTempProduct(Product selectedProduct){

        tempProductList.remove((selectedProduct));

    }

    /**
     * clearTempProdList clears temp product list after use.
     */
    public static void clearTempProdList() {
        tempProductList.clear();
    }

    /**
     *
     * @return part list
     */
    public static ObservableList<Part> getAllParts(){
        return partList;

    }

    /**
     *
     * @return temp part list
     */
    public static ObservableList<Part> getAllTempParts(){
        return tempPartList;

    }





    /**
     *
     * @return product list
     */
    public static ObservableList<Product> getAllProducts(){

        return productList;

    }

    /**
     *
     * @return temp product list
     */
    public static ObservableList<Product> getAllTempProducts(){

        return tempProductList;

    }




}










