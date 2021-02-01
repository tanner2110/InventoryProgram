package sample;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 11/11/2020
 * ConfirmWindowController is the controller for the pop up window that confirms delete and remove of items.
 */
public class ConfirmWindowController {

    /**
     * par1 and product1 are where the going to be deleted objects are stored until confirmed deleted.
     */
    private static Part part1;
    private static Product product1;

    private static Stage confirmWindow = new Stage();

    /**
     *
     * @param part sends part to confirm box class for delete
     * @throws IOException
     * showConfirmBoxPart brings up a confirm delete part box
     */
    public static void showConfirmBoxPart(Part part) throws IOException {


        Parent confirmRoot = FXMLLoader.load(ConfirmWindowController.class.getResource("ConfirmWindowPart.fxml"));
        Scene scene = new Scene(confirmRoot);
        confirmWindow.setScene(scene);
        confirmWindow.show();
        part1 = part;


    }

    /**
     *
     * @param product
     * @throws IOException
     * showConfirmBoxProduct brings up a confirm delete product box
     */
    public static void showConfirmBoxProduct(Product product) throws IOException {


        Parent confirmRoot = FXMLLoader.load(ConfirmWindowController.class.getResource("ConfirmWindowProd.fxml"));
        Scene scene = new Scene(confirmRoot);
        confirmWindow.setScene(scene);
        confirmWindow.show();
        product1 = product;


    }

    /**
     *
     * @param mouseEvent delete part from list
     */
    public void yesButClicked(MouseEvent mouseEvent) {

        Inventory.deletePart(part1);
        Inventory.deleteTempPart(part1); //have to delete temp part to in case trying to delete a part when searching other wise it does not show deleted until you type something
        confirmWindow.close();
    }

    /**
     *
     * @param mouseEvent closes confirm delete box without deleting
     */
    public void noButClicked(MouseEvent mouseEvent) {
        confirmWindow.close();
    }

    /**
     *
     * @param mouseEvent confirms delete of a product and does it
     */
    public void yesButClickedProd(MouseEvent mouseEvent) {
        Inventory.deleteProduct(product1);
        Inventory.deleteTempProduct(product1);
        confirmWindow.close();
    }

    /**
     *
     * @param mouseEvent closes delete product confirm box without deleting
     */
    public void noButClickedProd(MouseEvent mouseEvent) {
        confirmWindow.close();
    }
}
