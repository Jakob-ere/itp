package ui;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ContactUsTest extends ApplicationTest {
    

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("contactus.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 1000, 600));
        stage.show();
        setup();
    }

    public static void setup() {

    }

    @Test
    public void verifyUiSetup() {
        verifyThat("#F_button", hasText("Finance"));
        verifyThat("#PI_button", hasText("Personal Information"));
        verifyThat("#S_button", hasText("Settings"));
    }

}
