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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * @author Oskar Nesheim
 */
public class SettingsController {

    @FXML
    private BorderPane borderpane = new BorderPane();

    /**
     * label for username, mail and userID
     */
    @FXML
    private Label userID, name, mail;
    /**
     * Buttons in the fxml file
     * F_button - sets the scene to finance.fxml
     * PI_button - sets the scene to personalinformation.fxml
     * CU_button - sets the scene to contactus.fxml
     */
    @FXML
    private Button F_button, PI_button, CU_button;

    /**
     * this is the user that is currently logged in.
     */
    private User currentUser;

    /**
     * This is a collection of all coins from API
     */
    private Collection<Coin> allCoins;

    /**
     * Initializes the current user on this controller. This lets us delived data
     * between scenes
     * and controllers
     * 
     * @param user     - user object that is logged in
     * @param allCoins - all legal coins from API
     * @throws ConnectException
     * 
     */
    public void initData(final String userId, final Collection<Coin> allCoins) throws ConnectException {
        this.currentUser = RequestManagement.getUserWithID(userId);
        this.allCoins = new ArrayList<>(allCoins);
        setUserName(currentUser.getName());
        setMail(currentUser.getEmail());
        setUserID(currentUser.getUserId().toString());
    }

    /**
     * 
     * @return - user that is currently logged in.
     */
    private User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * 
     * @param value - string text of what the userID is
     */
    private void setUserID(String value) {
        this.userID.setText(value);
    }

    /**
     * sets the username label
     * 
     * @param value - string text of what the username is
     */
    private void setUserName(String value) {
        this.name.setText(value);
    }

    /**
     * sets the mail label
     * 
     * @param value - string text of what the mail is
     */
    private void setMail(String value) {
        this.mail.setText(value);
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
     * changes the scene
     * 
     * @param event - buttonclickevent in fxml
     * @throws IOException - if file is not found
     */
    @FXML
    private void setContactUs(ActionEvent event) throws IOException {
        SceneChanger.changeStage(this, event, "contactus", getCurrentUser().getUserId().toString(), allCoins);
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
