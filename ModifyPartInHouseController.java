package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 11/11/2020
 * ModifyPartInHouseController is controller of in house parts page
 */
public class ModifyPartInHouseController implements Initializable {


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
    private TextField machineIdTB;

    /**
     * selectedPart is used to store the part that is being updated
     */
    InHouse selectedPart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedPart = (InHouse) Inventory.getUpdatingPart();
        idTB.setText(Integer.toString(selectedPart.getId()));
        maxTB.setText(Integer.toString(selectedPart.getMax()));
        minTB.setText(Integer.toString(selectedPart.getMin()));
        nameTB.setText(selectedPart.getName());
        inventoryTB.setText(Integer.toString(selectedPart.getStock()));
        priceTB.setText(Double.toString(selectedPart.getPrice()));
        machineIdTB.setText(Integer.toString(selectedPart.getMachineId()));

    }

    /**
     *
     * @param mouseEvent save part
     * @throws IOException
     * ModPartSaveBut saves changes to part in house
     */
        public void ModPartSaveBut(MouseEvent mouseEvent) throws IOException {
            selectedPart.setId(Integer.valueOf(idTB.getText()));
            selectedPart.setMax((Integer.valueOf(maxTB.getText())));
            selectedPart.setMin(Integer.valueOf(minTB.getText()));
            selectedPart.setPrice(Double.valueOf(priceTB.getText()));
            selectedPart.setStock(Integer.valueOf(inventoryTB.getText()));
            selectedPart.setName(nameTB.getText());
            selectedPart.setMachineId(Integer.valueOf(machineIdTB.getText()));
            Main.loadMainPage();
    }

    /**
     *
     * @param mouseEvent cancel
     * @throws Exception
     * ModPartCancelBut cancels changes to part exits
     */
    public void ModPartCancelBut(MouseEvent mouseEvent) throws Exception {

        Main.loadMainPage();
    }
}
