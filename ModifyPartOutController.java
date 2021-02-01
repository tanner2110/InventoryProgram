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
 * ModifyPartOutController is the controller of out sourced parts page
 */
public class ModifyPartOutController implements Initializable {



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
    private TextField companyNameTB;

    /**
     * selectedPart is used to store the part that is being modified
     */
    OutSourced selectedPart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedPart = (OutSourced) Inventory.getUpdatingPart();
        idTB.setText(Integer.toString(selectedPart.getId()));        //retrieves the object and sets its text boxes
        maxTB.setText(Integer.toString(selectedPart.getMax()));
        minTB.setText(Integer.toString(selectedPart.getMin()));
        nameTB.setText(selectedPart.getName());
        inventoryTB.setText(Integer.toString(selectedPart.getStock()));
        priceTB.setText(Double.toString(selectedPart.getPrice()));
        companyNameTB.setText(selectedPart.getCompanyName());

    }


    /**
     *
     * @param mouseEvent save part
     * @throws IOException
     * SaveModPartBut saves changes to out sourced part
     */
    public void SaveModPartBut(MouseEvent mouseEvent) throws IOException {

        selectedPart.setId(Integer.valueOf(idTB.getText()));
        selectedPart.setMax((Integer.valueOf(maxTB.getText())));
        selectedPart.setMin(Integer.valueOf(minTB.getText()));
        selectedPart.setPrice(Double.valueOf(priceTB.getText()));
        selectedPart.setStock(Integer.valueOf(inventoryTB.getText()));
        selectedPart.setName(nameTB.getText());
        selectedPart.setCompanyName(companyNameTB.getText());
        Main.loadMainPage();


    }

    /**
     *
     * @param mouseEvent cancel
     * @throws Exception
     * CancelModPartBut cancels changes
     */
    public void CancelModPartBut(MouseEvent mouseEvent) throws Exception {

        Main.loadMainPage();
    }
}
