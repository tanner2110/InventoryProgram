package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 11/11/2020
 *ModifyProductController is the controller of the modify product page
 */
public class ModifyProductController implements Initializable {

    /**
     * selectedProduct is used to store the current product that is being modified
     */
    private static Product selectedProduct;

    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> idCol;
    @FXML
    private TableColumn<Part, String> nameCol;   // parts table columns
    @FXML
    private TableColumn<Part, Integer> inventoryCol;
    @FXML
    private TableColumn<Part, Double> priceCol;

    //////////

    @FXML
    private TableView<Part> asPartTable;
    @FXML
    private TableColumn<Part, Integer> asIdCol;
    @FXML
    private TableColumn<Part, String> asNameCol;   // parts table columns
    @FXML
    private TableColumn<Part, Integer> asInventoryCol;
    @FXML
    private TableColumn<Part, Double> asPriceCol;


    @FXML
    private TextField nameTB;
    @FXML
    private TextField inventoryTB;
    @FXML
    private TextField maxTB;
    @FXML
    private TextField priceTB;
    @FXML
    private TextField minTB;
    @FXML
    private TextField idTB;

    @FXML
    private TextField partSearchBox;

    @FXML
    private Label errorLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedProduct = Inventory.getUpdatingProduct();
        idTB.setText(Integer.toString(selectedProduct.getId()));
        maxTB.setText(Integer.toString(selectedProduct.getMax()));
        minTB.setText(Integer.toString(selectedProduct.getMin()));
        nameTB.setText(selectedProduct.getName());
        inventoryTB.setText(Integer.toString(selectedProduct.getStock()));
        priceTB.setText(Double.toString(selectedProduct.getPrice()));




        partTable.setItems(Inventory.getAllParts());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));



        for (Part part : selectedProduct.getAllAssociatedParts()) {   // for loop to add associated parts to a temp list to prevent deletion from actual list
            Inventory.addTempPart(part);}
        asPartTable.setItems(Inventory.getAllTempParts());
        asIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asInventoryCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        asPriceCol.setCellValueFactory(new PropertyValueFactory<>("stock"));



    }

    /**
     *
     * @param mouseEvent save prod
     * @throws IOException
     * ModProdSaveBut saves changes to product
     */
    public void ModProdSaveBut(MouseEvent mouseEvent) throws IOException {

        try {
            if (Integer.parseInt(minTB.getText()) < Integer.parseInt(maxTB.getText())) {
                selectedProduct.clearAssociatedParts();
                selectedProduct.addAssociatedParts(Inventory.getAllTempParts());
                selectedProduct.setId(Integer.valueOf(idTB.getText()));
                selectedProduct.setMax((Integer.valueOf(maxTB.getText())));
                selectedProduct.setMin(Integer.valueOf(minTB.getText()));
                selectedProduct.setPrice(Double.valueOf(priceTB.getText()));
                selectedProduct.setStock(Integer.valueOf(inventoryTB.getText()));
                selectedProduct.setName(nameTB.getText());
                Main.loadMainPage();
                Inventory.clearTempPartList();
            } else {
                errorLabel.setText("Max must be greater than min.");
            }
        } catch (NumberFormatException | IOException e){
                errorLabel.setText("Enter proper values in each box.");
        }
    }

    /**
     *
     * @param mouseEvent cancel mod prod
     * @throws Exception
     * ModProdCancelBut cancels changes to product, clears temp associated parts list
     */
    public void ModProdCancelBut(MouseEvent mouseEvent) throws Exception {

        Main.loadMainPage();
        Inventory.clearTempPartList();
    }

    /**
     *
     * @param mouseEvent remove associated part
     * @throws IOException
     * ModProdRemoveAssocPartBut removes associated part
     */
    public void ModProdRemoveAssocPartBut(MouseEvent mouseEvent) throws IOException {

        ConfirmWindowController.showConfirmBoxPart(asPartTable.getSelectionModel().getSelectedItem());
    }

    /**
     *
     * @param mouseEvent add prod
     * ModProdAddBut adds to associated part list
     */
    public void ModProdAddBut(MouseEvent mouseEvent) {

        if (partTable.getSelectionModel().getSelectedItem() != null) {
            Inventory.addTempPart(partTable.getSelectionModel().getSelectedItem());
        }                                                     //end if

    }


    //start search methods
    /////////////////////////////////////////////////////////////////////////////////////////////
    /**
     *
     * @param keyEvent search box key press activates search methods
     */
    public void partSearch(KeyEvent keyEvent) {
        showPartSearchList();

    }

    /////////////////////////////////////////////////////////////////////
    /**
     * similar method to previous, checks if search box empty or full
     * showPartSearchList similar to previous search methods, starts order of three methods that search the object
     */
    public void showPartSearchList(){

        if (partSearchBox.getText().equals("") || partSearchBox.getText() == null){  //checks if search box is empty
            partTable.setItems(Inventory.getAllParts());
        }
        else {
            Inventory.clearTempPartList(); //clears list each time key pressed for new results
            for (Part part : listOfMatchedParts(Inventory.getAllParts())) {  //called if search box not empty
                Inventory.addTempPart(part);}
            partTable.setItems(Inventory.getAllTempParts());
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * similar to previous, sends each part to method to check if matched
     * @param list of matched parts
     * @return matched parts
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
     * similar to previous, compares objects name and id to typed text to see if a match is found.
     * @param typedText search box text
     * @param comparedPart current part comparing
     * @return true if match
     */
    public boolean partTextCompare(String typedText, Part comparedPart){  //each part is sent here along with search text to see if the name matches.
        /////////////start of name search
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

        //////////////////////start of id search
        String[] idSplit = Integer.toString(comparedPart.getId()).split("(?!^)");
        splitPart = "";
        for(int s = 0; s < textSize; s++){  //adds parts of split text into partial word to match for typed text
            if(idSplit.length == s) break; //done to keep array parts from going out of bounds of key types longer than part name
            splitPart = splitPart.concat(idSplit[s]);
        }
        if(splitPart.equals(text)) return true;  //checks to see if typed text is same as part id text
            ////////////////end id search

        else return false;
    }


    //end search for part;





}
