package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 
 * @author Oskar Nesheim
 * @author Casper Andreassen
 * @author Jakob Relling
 * @author Ole Dahl
 */
public class CCApp extends Application {

    /**
     * Creates a new scene and opens it.
     * 
     * @param stage - the stage the program will start up at
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CCApp.class.getResource("welcomescreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Welcome to Cryptocojo!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Starts the program
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}
