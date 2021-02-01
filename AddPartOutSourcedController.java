package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 11/11/2020
 *AddPartOutSourcedController is the controller for the add part out sourced page
 */
public class AddPartOutSourcedController implements Initializable {



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
    private TextField companyNameTB;

    @FXML
    private RadioButton outRadioButton;

    @FXML
    private Label errorLabel;

    /**
     * int id is used for storing product or part id
     */
    private int id;  //for the part id


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        outRadioButton.fire();

    }

    /**
     *
     * @param mouseEvent save button add part
     */
    public void AddPartSaveButOut(MouseEvent mouseEvent) {

        try {
            if (Integer.parseInt(maxTB.getText()) > Integer.parseInt(minTB.getText())) { // check if min is less than max
                id = Inventory.createId();
                String name = nameTB.getText();
                double price = Double.parseDouble(priceTB.getText());
                int inventory = Integer.parseInt(inventoryTB.getText());
                int min = Integer.parseInt(minTB.getText());
                int max = Integer.parseInt(maxTB.getText());
                String companyName = companyNameTB.getText();
                OutSourced part = new OutSourced(id, name, price, inventory, min, max, companyName);
                Inventory.addPart(part);
                Main.loadMainPage();
        }
        else {errorLabel.setText("Max must be greater than min");}
        } catch (NumberFormatException | IOException e) {
            errorLabel.setText("Enter proper values in each box");
        }
    }

    /**
     *
     * @param mouseEvent cancel button add part screen
     * @throws Exception
     */
    public void AddPartCancelButOut(MouseEvent mouseEvent) throws Exception {

        Main.loadMainPage();
    }


    /**
     *
     * @param mouseEvent loads in house part form
     * @throws Exception
     */
    public void InHouseRadioClick(MouseEvent mouseEvent) throws Exception {

        Parent modifyPartRoot = FXMLLoader.load(getClass().getResource("AddPartInHouse.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        Main.getPrimaryStage().setScene(modifyPartButClick);
        Main.getPrimaryStage().show();

    }
}
