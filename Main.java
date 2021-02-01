package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * @author Tanner Freemon
 * @version 1.0
 * @date 11/11/2020
 * Main page
 */
public class Main extends Application {

private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        Parent mainRoot = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        Scene mainPage = new Scene(mainRoot);
        primaryStage.setOnCloseRequest(e -> onClose()); // calls on close method when program x button exit used.
        primaryStage.setTitle("Bike Factory Inventory");
        primaryStage.setScene(mainPage);
        primaryStage.show();
    }


    public static void main(String[] args) {


        InHouse bolt = new InHouse(9, "bolt", .99, 100, 1, 1000, 99);
        InHouse tire = new InHouse(999, "tire", 10.99, 100, 1, 1000, 99);
        InHouse brakes = new InHouse(1000, "brakes", 9.40, 100, 1, 1000, 99);
        InHouse gear = new InHouse(1001, "gear", 5.00, 100, 1, 1000, 99);
        InHouse frame = new InHouse(1004, "frame", 7, 2, 1, 1000, 99);
        Product bike = new Product(9492, "Bike", 199.99, 100, 1, 100);
        Product scooter = new Product(10092, "Scooter", 92.99, 200, 1, 200);
        Inventory.addPart(brakes);
        Inventory.addPart(bolt);
        Inventory.addPart(tire);
        Inventory.addPart(gear);
        Inventory.addPart(frame);
        Inventory.addProduct(bike);
        Inventory.addProduct(scooter);
        launch(args);

    }


    /**
     * on close will save changes made when program exited by the top x
     * or this is where you could implement that feature
     */
    public void onClose(){
        System.out.println("file saved");

    }


    /**
     *
     * @return primary stage to methods in controllers to allow changing of scene.
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }


    /**
     *
     * @throws IOException method to load main page
     */
    public static void loadMainPage() throws IOException {
        Parent modifyPartRoot = FXMLLoader.load(Main.class.getResource("MainForm.fxml"));
        Scene modifyPartButClick = new Scene(modifyPartRoot);
        getPrimaryStage().setScene(modifyPartButClick);
        getPrimaryStage().show();
    }


}
