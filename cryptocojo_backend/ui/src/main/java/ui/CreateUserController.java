package ui;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

import core.items.Coin;
import core.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateUserController {

    /**
     * textfield for the user to enter username
     */
    @FXML
    private TextField username = new TextField();

    /**
     * textfield for the user to enter mail
     */
    @FXML
    private TextField mail = new TextField();

    /**
     * passwordfield for the user to enter password
     */
    @FXML
    private PasswordField password = new PasswordField();

    /**
     * passwordfield for the user to enter password
     */
    @FXML
    private Label errorMessage = new Label();

    /**
     * 
     * @return - username from fxml file
     */
    public String getUsername() {
        return username.getText().toString();
    }

    /**
     * a collection of all coins available from CoinCap API.
     */
    private Collection<Coin> allCoins;

    /**
     * 
     * @return - mail from fxml file
     */
    public String getMail() {
        return mail.getText().toString();
    }

    /**
     * 
     * @return - password from fxml file
     */
    public String getPassword() {
        return password.getText().toString();
    }

    /**
     * Method for creating a new user.
     * 
     * @param event - button click from fxml file
     */
    @FXML
    private void createNewUser(ActionEvent event) {
        if (getUsername().isEmpty() || getMail().isEmpty() || getPassword().isEmpty()) {
            this.errorMessage.setText("Du må fylle inn alle feltene.");
            return;
        }
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if (!Pattern.compile(regexPattern).matcher(getMail()).matches()) {
            this.errorMessage.setText("Du må oppgi en gyldig mail");
            return;
        }
        this.allCoins = RequestManagement.getAllCoins();
        try {
            User user = RequestManagement.createNewUser(getUsername(), getPassword(), getMail());
            SceneChanger.changeStage(this, event, "personalinformation", user.getUserId().toString(), allCoins);
        } catch (Exception e) {
            this.errorMessage.setText("Kunne ikke lage bruker.");
            System.out.println(e);
        }
    }

    /**
     * Changes the scene.
     * Does not takes a user to the next scene.
     *
     * @param event - button click from fxml file
     * @throws IOException - if file is not found
     */
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        SceneChanger.changeStage(this, event, "welcomescreen", null, null);
    }
}
