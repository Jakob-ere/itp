package ui;

import java.io.IOException;
import java.util.Collection;

import core.items.Coin;
import core.user.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * @author Oskar Nesheim
 */
public class SceneChanger {
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGTH = 600;
    /*
     * stage for changing
     */
    private static Stage stage;

    /*
     * scene for changing
     */
    private static Scene scene;

    /*
     * @param c - the controllerclass of the last scene
     * 
     * @param event - the Actionevent the new stage gets window from
     * 
     * @param fxmlFile - what fxml file the next controller should get ui
     * information from
     * 
     * @param user - the current user
     * 
     * @param allCoins - all available coins from CoinCap API
     */
    public static void changeStage(
            Object c, ActionEvent event,
            String fxmlFile, String userId,
            Collection<Coin> allCoins)
            throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(c.getClass().getResource(fxmlFile + ".fxml"));
        loader.load();

        if (fxmlFile.equals("personalinformation")) {
            PersonalInformationController controller = loader.getController();
            controller.initData(userId, (Collection<Coin>) allCoins);
        } else if (fxmlFile.equals("finance")) {
            FinanceController controller = loader.getController();
            controller.initData(userId, allCoins);
        } else if (fxmlFile.equals("contactus")) {
            ContactUsController controller = loader.getController();
            controller.initData(userId, allCoins);
        } else if (fxmlFile.equals("settings")) {
            SettingsController controller = loader.getController();
            controller.initData(userId, allCoins);
        }

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loader.getRoot(), SCREEN_WIDTH, SCREEN_HEIGTH);
        stage.setScene(scene);
        stage.show();
    }

}
