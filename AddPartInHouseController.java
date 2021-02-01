package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 11/11/2020
 * AddPartInHouseController is controller class for the part in house page
 */
public class AddPartInHouseController implements Initializable {


    @FXML
    private TextField AddPartInNameTB;
    @FXML
    private TextField AddPartInInvoTB;
    @FXML
    private TextField AddPartInMaxTB;
    @FXML
    private TextField AddPartInPriceTB;
    @FXML
    private TextField AddPartInMinTB;
    @FXML
    private TextField AddPartInMachineIdTB;

    @FXML
    private RadioButton inHouseRadio;

    @FXML
    private Label errorLabel;

    /**
     * int id is used for storing product or part id
     */
    private int id;  //for the part id


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        inHouseRadio.fire();

    }


    /**
     *
     * @param mouseEvent save button that adds a part to the list
     */
    public void AddPartSaveBut(MouseEvent mouseEvent) {

        try {
            if (Integer.parseInt(AddPartInMaxTB.getText()) > Integer.parseInt(AddPartInMinTB.getText())) { // check if min is less than max
                id = Inventory.createId();
                String name = AddPartInNameTB.getText();
                double price = Double.parseDouble(AddPartInPriceTB.getText());
                int inventory = Integer.parseInt(AddPartInInvoTB.getText());
                int min = Integer.parseInt(AddPartInMinTB.getText());
                int max = Integer.parseInt(AddPartInMaxTB.getText());
                int machineId = Integer.parseInt(AddPartInMachineIdTB.getText());


                InHouse part = new InHouse(id, name, price, inventory, min, max, machineId);
                Inventory.addPart(part);
                Main.loadMainPage();
            }//end first if
            else {errorLabel.setText("Max must be greater than min");}
        }
        catch (NumberFormatException | IOException e) {
            errorLabel.setText("Enter proper values in each box.");
        }

}

    /**
     *
     * @param mouseEvent cancel button for add part form
     * @throws Exception
     */
    public void AddPartCancelBut(MouseEvent mouseEvent) throws Exception {

        Main.loadMainPage();

    }


    /**
     *
     * @param mouseEvent  radio button that changes the add part from inhouse to outsourced.
     * @throws Exception
     */
    public void AddPartRadioOut(MouseEvent mouseEvent) throws Exception {

        Parent modifyPartRoot = FXMLLoader.load(getClass().getResource("AddPartOutSourced.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        Main.getPrimaryStage().setScene(modifyPartButClick);
        Main.getPrimaryStage().show();


    }


}
