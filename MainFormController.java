package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 11/11/2020
 * MainFormController has a lot on it, it directs you to all the other pages and displays all of the inventory
 */
public class MainFormController implements Initializable {



    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partId;
    @FXML
    private TableColumn<Part, String> partName;   // parts table columns
    @FXML
    private TableColumn<Part, Integer> inventory;
    @FXML
    private TableColumn<Part, Double> price;


    ///////////////////////


    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;   //product table columns
    @FXML
    private TableColumn<Product, Integer> inventoryCol;
    @FXML
    private TableColumn<Product, Double> priceCol;

    @FXML
    private  TextField productSearchBox;
    @FXML
    private  TextField partSearchBox;

    @FXML
    private Label deleteWarning;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        productTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


        partsTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory.setCellValueFactory(new PropertyValueFactory<>("stock"));


    }

/**
 * A feature I would implement in the future would most likely be a recycling bin that would let you restore parts or products
 * that have been deleted. It would let you look back at past parts and products and also restore them in needed.
 */


    /**
     * @param mouseEvent modify part button, loads modify screen
     * @throws Exception
     */
    public void ModPartButClick(MouseEvent mouseEvent) throws Exception {


        if (partsTable.getSelectionModel().getSelectedItem() != null){
            Inventory.storeUpdatingPart(partsTable.getSelectionModel().getSelectedItem()); //stores selected object can retrieve with other method

            if (partsTable.getSelectionModel().getSelectedItem().checkClass().equals("InHouse")){  //if else checks for in house or out sourced class
                Parent modifyPartRoot = FXMLLoader.load(getClass().getResource("ModifyPartInHouse.fxml"));
                Scene modifyPartButClick = new Scene(modifyPartRoot);
                Main.getPrimaryStage().setScene(modifyPartButClick);
                Main.getPrimaryStage().show();}


            else {
                Parent modifyPartRoot = FXMLLoader.load(getClass().getResource("ModifyPartOut.fxml")); //loads outsourced form if if statement fails
                Scene modifyPartButClick = new Scene(modifyPartRoot);
                Main.getPrimaryStage().setScene(modifyPartButClick);
                Main.getPrimaryStage().show();}

    }

    }


    /**
     *
     * @param mouseEvent exit button
     */
    public void ExitBut(MouseEvent mouseEvent) {

        Main.getPrimaryStage().close();
    }



    /**
     *
     * @param mouseEvent delete product button
     */
    public void DeleteProdBut(MouseEvent mouseEvent) throws IOException {

        if (productTable.getSelectionModel().getSelectedItem() != null) {  //checks to make sure an object is selected before delete
            if (productTable.getSelectionModel().getSelectedItem().checkForParts() == false) {  //calls method in selected object to see if it has assoc parts
                ConfirmWindowController.showConfirmBoxProduct(productTable.getSelectionModel().getSelectedItem());
            } //end second if
            else {
                deleteWarning.setText("Can't delete product with associated parts");
            }

        }//end first if.
    }
    /**
     *
     * @param mouseEvent modify product button
     */
    public void ModProdBut(MouseEvent mouseEvent) throws Exception {

        if (productTable.getSelectionModel().getSelectedItem() != null) {
            Inventory.storeUpdatingProduct(productTable.getSelectionModel().getSelectedItem()); //store selected item can retrieve with other method
            Parent modifyPartRoot = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
            Scene modifyPartButClick = new Scene(modifyPartRoot);
            Main.getPrimaryStage().setScene(modifyPartButClick);
            Main.getPrimaryStage().show();
        }//end if
    }



    /**
     *
     * @param mouseEvent add product button
     */
    public void AddProdBut(MouseEvent mouseEvent) throws Exception {

        Parent modifyPartRoot = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        Main.getPrimaryStage().setScene(modifyPartButClick);
        Main.getPrimaryStage().show();

    }


    /**
     *
     * @param mouseEvent delete part button
     */
    public void DeletePartBut(MouseEvent mouseEvent) throws IOException {

        ConfirmWindowController.showConfirmBoxPart(partsTable.getSelectionModel().getSelectedItem());

    }



    /**
     *
     * @param mouseEvent add part button
     */
    public void AddPartBut(MouseEvent mouseEvent) throws Exception{

        Parent modifyPartRoot = FXMLLoader.load(getClass().getResource("AddPartInHouse.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        Main.getPrimaryStage().setScene(modifyPartButClick);
        Main.getPrimaryStage().show();
    }







///////start search methods

    /**
     *
     * @param keyEvent
     * SearchPartTextBox method is the first of a series of methods that do the search box function. It is activated
     * once a key is pressed. It calls a total of three other methods that take the part list, break it into individual parts
     * takes the name and id of those parts, breaks those properties into chunks that are the same size of the search box text
     * and compares them to see if they are the same. Once a match is found, it is added to a temporary list that is eventually displayed.
     * The following 4 methods are pretty similar throughout the rest of the classes that require a search function. There are small
     * tweaks for different objects.
     */
    public void SearchPartTextBox(KeyEvent keyEvent) {
        showPartSearchList(); //starts part search and display in table with 3 below methods that do the job on key press

    }
  /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * showPartSearchList checks the search box for text, sends on to next method if has text
     */
  public void showPartSearchList(){

        if (partSearchBox.getText().equals("") || partSearchBox.getText() == null){  //checks if search box is empty
            partsTable.setItems(Inventory.getAllParts());
        }
        else {
                Inventory.clearTempPartList();
            for (Part part : listOfMatchedParts(Inventory.getAllParts())) {
                Inventory.addTempPart(part);}
                                                                        //called if search box not empty
            partsTable.setItems(Inventory.getAllTempParts());
        }
    }
///////////////////////////////////////////////////////////////////////////////////

    /**
     *
      * @param list of matched parts
     * @return
     * listOfMatchedParts breaks down list of parts and sends on to next method to see if property matches search box, if does
     * it adds to a temp list
     */
public ObservableList<Part> listOfMatchedParts (ObservableList<Part> list) {

    ObservableList<Part> matchedParts = FXCollections.observableArrayList(); //another temporary list to hold searched parts
    for (Part part : list) {
        if(partTextCompare(partSearchBox.getText(),  part)){  //this line checks if part and typed text are the same and adds part in next line if are, sends to method below to check
            matchedParts.add(part);
        }

    }

    return matchedParts;
}

 //////////////////////////////////////////////////////////////////////////////////////

    /**
     * I had a runtime error in this method. Whenever I was typing in the search box to look for parts, whenever i always typed more than
     * four characters, I would get an array out of bounds error. This was because the array parts in the below method would be the size of the name of the current
     * part that is being tested to see if it is a match. But once I typed a string longer then the length of the first part name that is tested, the out of bounds
     * error would occur. The first part name was bolt and would always go out of bounds on the 5th key press. I corrected it by putting an if statement right above the spot the
     * out of bounds check would occur.
     * @param typedText
     * @param comparedPart
     * @return
     * partTextCompare breaks down the part properties and compares them to the search box text
     */
 public boolean partTextCompare(String typedText, Part comparedPart){  //each part is sent here along with search text to see if the name matches.
     //start of name search
     String[] nameSplit = comparedPart.getName().split("(?!^)");
     String text = typedText;
     String splitPart = "";
     int textSize = typedText.length();
     for(int s = 0; s < textSize; s++){
         //RIGHT HERE, is where I put in the code to stop the method from exceeding array and return false, because if it is longer than the name, it is not a match anyways.
         if(nameSplit.length == s) break; //done to keep array parts from going out of bounds of key types longer than part name
         splitPart = splitPart.concat(nameSplit[s]);//creates a string of same length as search box text to see if matches part name
     }
     if(splitPart.toLowerCase().equals(text)) return true;  //checks to see if typed text is same as part name text

     //start of id search
     String[] idSplit = Integer.toString(comparedPart.getId()).split("(?!^)");
     splitPart = "";
     for(int s = 0; s < textSize; s++){  //adds parts of split text into partial word to match for typed text
         if(idSplit.length == s) break; //done to keep array parts from going out of bounds of key types longer than part name
         splitPart = splitPart.concat(idSplit[s]);
     }
     if(splitPart.equals(text)) return true;  //checks to see if typed text is same as part id text
     //end id search

     else return false;
 }
 //end search for part;

 ////////////////////////////////////////////////////////////////////////////

    // start of product search methods

    /**
     *
     * @param keyEvent
     * SearchProdTextBox starts product search
     */
    public void SearchProdTextBox(KeyEvent keyEvent) {

     showProductSearchList();

    }

    /////////////////////////////////////////////////////////////

    /**
     * showProductSearchList checks product search box
     */
    public void showProductSearchList(){

        if (productSearchBox.getText().equals("") || productSearchBox.getText() == null){  //checks if search box is empty
            productTable.setItems(Inventory.getAllProducts());
        }
        else {
            Inventory.clearTempProdList();
            for (Product product : listOfMatchedProducts(Inventory.getAllProducts())) { //called if search box not empty
                Inventory.addTempProduct(product);}
            productTable.setItems(Inventory.getAllTempProducts());
        }
    }


    ///////////////////////////////////////////////////

    /**
     *
     * @param list of matched products
     * @return
     * listOfMatchedProducts breaks down product list and sends to compare
     */
    public ObservableList<Product> listOfMatchedProducts (ObservableList<Product> list) {

        ObservableList<Product> matchedProducts = FXCollections.observableArrayList(); //another temporary list to hold searched products
        for (Product product : list) {
            if(productTextCompare(productSearchBox.getText(),  product)){  //this line checks if part and typed text are the same and adds part in next line if are, sends to method below to check
                matchedProducts.add(product);
            }

        }

        return matchedProducts;
    }

    ///////////////////////////////////////////////

    /**
     *
     * @param typedText search box
     * @param comparedProduct product comapring to text
     * @return
     * productTextCompare compares product properties with search text
     */
    public static boolean productTextCompare(String typedText, Product comparedProduct){

        //start of name search
        String[] nameSplit = comparedProduct.getName().split("(?!^)");
        String text = typedText;
        String splitProduct = "";
        int textSize = typedText.length();
        for(int s = 0; s < textSize; s++){
            //RIGHT HERE, is where I put in the code to stop the method from exceeding array and return false, because if it is longer than the name, it is not a match anyways.
            if(nameSplit.length == s) break; //done to keep array parts from going out of bounds of key types longer than part name
            splitProduct = splitProduct.concat(nameSplit[s]);//creates a string of same length as search box text to see if matches part name
        }
        if(splitProduct.toLowerCase().equals(text)) return true;  //checks to see if typed text is same as part name text

        //start of id search
        String[] idSplit = Integer.toString(comparedProduct.getId()).split("(?!^)");
        splitProduct = "";
        for(int s = 0; s < textSize; s++){  //adds parts of split text into partial word to match for typed text
            if(idSplit.length == s) break; //done to keep array parts from going out of bounds of key types longer than part name
            splitProduct = splitProduct.concat(idSplit[s]);
        }
        if(splitProduct.equals(text)) return true;  //checks to see if typed text is same as part id text
            // end id search

        else return false;

    }

}





