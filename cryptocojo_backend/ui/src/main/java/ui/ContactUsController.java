package ui;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collection;

import core.items.Coin;
import core.user.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author Oskar Nesheim
 * @author Jakob Relling
 */
public class ContactUsController {

    /**
     * Buttons in the fxml file
     * F_button - sets the scene to finance.fxml
     * S_button - sets the scene to settings.fxml
     * PI_button - sets the scene to personalinformation.fxml
     */
    @FXML
    private Button F_button, S_button, PI_button;

    /**
     * this is the user that is currently logged in.
     */
    private User currentUser;

    /**
     * a collection of all coins available from CoinCap API
     */
    private Collection<Coin> allCoins;

    /**
     * Initializes the current user on this controller. This lets us send data
     * between scenes and controllers
     * 
     * @param user     - user object that is logged in
     * @param allCoins - all legal coins from API
     * @throws ConnectException
     * 
     */
    public void initData(final String userId, final Collection<Coin> allCoins) throws ConnectException {
        this.currentUser = RequestManagement.getUserWithID(userId);
        this.allCoins = new ArrayList<>(allCoins);
    }

    /**
     * 
     * @return return the current user
     */
    private User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * changes the scene
     * 
     * @param event - buttonclickevent in fxml
     * @throws IOException - if file is not found
     */
    @FXML
    private void setPersonalInformation(ActionEvent event) throws IOException {
        SceneChanger.changeStage(this, event, "personalinformation", getCurrentUser().getUserId().toString(), allCoins);
    }

    /**
     * changes the scene
     * 
     * @param event - buttonclickevent in fxml
     * @throws IOException - if file is not found
     */
    @FXML
    private void setFinance(ActionEvent event) throws IOException {
        SceneChanger.changeStage(this, event, "finance", getCurrentUser().getUserId().toString(), allCoins);
    }

    /**
     * <h3>changes the scene
     * <h3/>
     * 
     * @param event - buttonclickevent in fxml
     * @throws IOException - if file is not found
     */
    @FXML
    private void setSettings(ActionEvent event) throws IOException {
        SceneChanger.changeStage(this, event, "settings", getCurrentUser().getUserId().toString(), allCoins);
    }

    /**
     * changes the scene
     * 
     * @param event - buttonclickevent in fxml
     * @throws IOException - if file is not found
     */
    @FXML
    private void loggOut(ActionEvent event) throws IOException {
        SceneChanger.changeStage(this, event, "welcomescreen", null, null);
    }

}
