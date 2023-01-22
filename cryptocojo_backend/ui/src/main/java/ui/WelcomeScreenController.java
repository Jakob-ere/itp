package ui;

import java.io.IOException;
import java.util.Collection;

import core.items.Coin;
import core.user.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author Oskar Nesheim
 */
public class WelcomeScreenController {

    /**
     * textfield where user can enter username.
     */
    @FXML
    private TextField username = new TextField();

    /**
     * passwordfield where user can enter password.
     */
    @FXML
    private PasswordField password = new PasswordField();

    /**
     * button for user to login.
     */
    @FXML
    private Button login;

    /**
     * label for errormessage that user gets in login is invalid.
     */
    @FXML
    private Label errorMessage;

    /**
     * a collection of all coins available from CoinCap API.
     */
    private Collection<Coin> allCoins;

    /**
     *
     * @return - user object username
     */
    private String getUserName() {
        return username.getText().toString();
    }

    /**
     *
     * @return - user object password
     */
    private String getUserPassword() {
        return password.getText().toString();
    }

    /**
     * The message is only shown if there is an error with login.
     *
     * @param errorMessage - the message the user gets if login is invalid
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage.setText(errorMessage);
    }

    /**
     * Logs the user in and send him to the next personal information scene
     * If there is a error with login, the user will get a message and nothing will
     * happen.
     *
     * @param event - button click from fxml file
     * @throws IOException - if the fxml file is not found
     */
    @FXML
    private void login(ActionEvent event) throws IOException {
        if (getUserName().isEmpty() || getUserPassword().isEmpty()) {
            setErrorMessage("You cannot leave a field blank!");
            return;
        }
        if (!getUserName().matches("[a-zA-Z]{2,}[0-9]*")) {
            setErrorMessage("Illegal username");
            return;
        }
        try {
            User user = RequestManagement.getUserWithUP(getUserName(), getUserPassword());
            this.allCoins = RequestManagement.getAllCoins();
            if (user != null || this.allCoins != null) {
                SceneChanger.changeStage(this, event, "personalinformation", user.getUserId().toString(), allCoins);
            } else {
                setErrorMessage("Could not login");
            }
        } catch (IllegalArgumentException e) {
            setErrorMessage("Incorrect username or password");
        }
    }

    /**
     * Snds the user to a new scene where (s)he can create a user object
     * 
     * @param event - button click from fxml file
     * @throws IOException - if the fxml file is not found
     */
    @FXML
    private void createUser(ActionEvent event) throws IOException {
        SceneChanger.changeStage(this, event, "createuser", null, null);
    }
}
