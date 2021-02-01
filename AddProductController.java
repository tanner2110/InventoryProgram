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
 * AddProductController is the controller for the add product page
 */
public class AddProductController implements Initializable {


    public static ObservableList<Part> tempList = FXCollections.observableArrayList();

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

    /////////


    @FXML
    private TableView<Part> partsTable;
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
    private TableView<Part> asPartsTable;
    @FXML
    private TableColumn<Part, Integer> asIdCol;
    @FXML
    private TableColumn<Part, String> asNameCol;   // parts table columns
    @FXML
    private TableColumn<Part, Integer> asInventoryCol;
    @FXML
    private TableColumn<Part, Double> asPriceCol;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField partSearchBox;

    /**
     * int id is used for storing product or part id
     */
    private int id;  //for the product id


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        Inventory.clearTempPartList();
        asPartsTable.setItems(Inventory.getAllTempParts());
        tempList.clear();
        asIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asInventoryCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        asPriceCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


        id = Inventory.createId();       //creates and sets id textbox
        idTB.setText(Integer.toString(id));


    }


    /**
     * @param mouseEvent saves changes made to product list
     */
    public void AddProdSaveBut(MouseEvent mouseEvent) {

        try {
            if( Integer.parseInt(minTB.getText()) < Integer.parseInt(maxTB.getText())) {
                String name = nameTB.getText();
                double price = Double.parseDouble(priceTB.getText());
                int inventory = Integer.parseInt(inventoryTB.getText());
                int min = Integer.parseInt(minTB.getText());
                int max = Integer.parseInt(maxTB.getText());


                Product product = new Product(id, name, price, inventory, min, max);
                Inventory.addProduct(product);
                product.addAssociatedParts(Inventory.getAllTempParts());  //adds associated parts to the list connected to object.
                Main.loadMainPage();
            }
            else {errorLabel.setText("Max must be greater than min");}
        } catch (NumberFormatException | IOException e) {
            errorLabel.setText("Enter proper values in each box.");
        }




    }

    /**
     * @param mouseEvent cancels and goes back to main form on click button
     * @throws Exception
     */
    public void AddProdCancelBut(MouseEvent mouseEvent) throws Exception {

        Main.loadMainPage();
    }

    /**
     * @param mouseEvent removes selected part from associated list on button click
     */
    public void RemoveAssocPartBut(MouseEvent mouseEvent) throws IOException {

        ConfirmWindowController.showConfirmBoxPart(asPartsTable.getSelectionModel().getSelectedItem());
    }

    /**
     * @param mouseEvent adds selected  to associated partlist on button click
     */
    public void AddProdBut(MouseEvent mouseEvent) {

        if (partsTable.getSelectionModel().getSelectedItem() != null) {
            Inventory.addTempPart(partsTable.getSelectionModel().getSelectedItem());
            asPartsTable.setItems(Inventory.getAllTempParts());
        } //end if
    }




  //start search methods
  /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *
     * @param keyEvent starts first in series of methods for searching for parts
     */
    public void partSearch(KeyEvent keyEvent) {
        showPartSearchList();                      //first in series, starts on key press in search box

    }

/////////////////////////////////////////////////////////////////////////////////////

    /**
     * this method checks to see if text box is empty or not, if not it sends on the list of parts, and a matched list is returned and stored in templist2
     */
  public void showPartSearchList(){

      if (partSearchBox.getText().equals("") || partSearchBox.getText() == null){  //checks if search box is empty
          partsTable.setItems(Inventory.getAllParts());
      }
      else {
          Inventory.clearTempPartList(); //clears list each time key pressed for new results
          for (Part part : listOfMatchedParts(Inventory.getAllParts())) {  //called if search box not empty
              Inventory.addTempPart(part);}
          partsTable.setItems(Inventory.getAllTempParts());
      }
  }


    ///////////////////////////////////////////////////////////////////////////////////

    /**
     *
     * @param list, the part list is sent here, and is for looped through, and each part is sent to the following method
     *              to see if the typed texct matched anything in the part
     * @return matched parts list
     */
    public ObservableList<Part> listOfMatchedParts (ObservableList<Part> list) {

        ObservableList<Part> matchedParts = FXCollections.observableArrayList(); //another temporary list to hold searched parts
        for (Part part : list) {          //searches each part in list, sends to next method to compare each objects data against textbox
            if(partTextCompare(partSearchBox.getText(),  part)){
                matchedParts.add(part);      //part added to matched parts list here if above method finds a match
            }

        }

        return matchedParts;
    }

    //////////////////////////////////////////////////////////////////////////////////////

    /**
     *
     * @param typedText
     * @param comparedPart, here is where the sent part is compared with the text. its name and id are broken down into an array
     *                      and then the searchbox text is sized, a string the same size is built out of the part name
     *                      and id, and it is compared to see if it is the same. Once the searched text exceeds the
     *                      size of the name or id, the method breaks.
     * @return true if match found
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
    } //end search for part;



}
