package ui;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import core.items.Coin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SceneChangerTest extends ApplicationTest {

    private Object controller;
    private static List<Coin> coinList = new ArrayList<>();
    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader = new FXMLLoader(this.getClass().getResource("settings.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 1000, 600));
        controller = fxmlLoader.getController();
        stage.show();
        setup();
    }

    private void setup() throws ConnectException {
        ((SettingsController) controller).initData("2f0e399c-2d67-4df0-a91f-136438499151", (ArrayList<Coin>) coinList); 
    }

    /**
     * Testing that the different buttons navigates correctly.
     */
    @Test
    public void testChangeStage() {
        // Click finance button
        clickOn("#F_button");
        verifyThat("#S_button", hasText("Settings"));
        verifyThat("#PI_button", hasText("Personal Information"));
        verifyThat("#CU_button", hasText("Contact Us"));
        // Click contact us button
        clickOn("#CU_button");
        verifyThat("#F_button", hasText("Finance"));
        verifyThat("#PI_button", hasText("Personal Information"));
        verifyThat("#S_button", hasText("Settings"));
        // Click settings button
        clickOn("#S_button");
        verifyThat("#F_button", hasText("Finance"));
        verifyThat("#PI_button", hasText("Personal Information"));
        verifyThat("#CU_button", hasText("Contact Us"));
    } 
}