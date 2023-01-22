package ui;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import com.google.gson.Gson;
import core.items.Coin;
import core.rest.DecodeCoinManager;
import core.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

/**
 * Tests operations associated with the Personal Information GUI
 */

public class PersonalInformationTest extends ApplicationTest {

    private static PersonalInformationController controller;
    private static User user;
    private static List<Coin> coinList;
    private static String testUserId = "65905c91-3358-4a4c-b7b3-44fccd99e3e2";

    /**
     * Starts up the scene for testing
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("personalinformation.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 1000, 600));
        controller = fxmlLoader.getController();
        stage.show();
        setup();
    }

    /**
     * Sets up required state for testing features in GUI. Uses a static coin data
     * file for consistent test results.
     *
     * @throws ConnectException
     */

    public static void setup() throws ConnectException {
        coinList = new ArrayList<>();
        Gson parser = new Gson();
        try (Reader reader = new FileReader(
                System.getProperty("user.dir").replace("/ui", "/core")
                        + "/src/main/resources/test/coin_test.json")) {
            DecodeCoinManager retrievedCoin = parser.fromJson(reader, DecodeCoinManager.class);
            coinList = retrievedCoin.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.initData(user.getUserId().toString(), (ArrayList<Coin>) coinList);
    }

    @BeforeAll
    public static void setupUser() throws ConnectException {
        try {
            user = RequestManagement.createNewUserWithId("olanormann", "1234", "ola.normann@mail.no", testUserId);
        } catch (IOException e) {
            System.out.println("Something went wrong with creating user");
        }

    }

    /**
     * Verify that UI has been properly set-up with required buttons
     */

    @Test
    public void verifyUiSetup() {
        verifyThat("#buyButton", hasText("Buy"));
        verifyThat("#sellButton", hasText("Sell"));
        verifyThat("#F_button", hasText("Finance"));
        verifyThat("#CU_button", hasText("Contact Us"));
        verifyThat("#S_button", hasText("Settings"));
        verifyThat("#proButton", hasText("Toggle pro-mode"));
        verifyThat("#logout_button", hasText("Logg out"));
    }

    /**
     * Tests that a user can toggle pro mode and new labels are added to GUI.
     *
     * @throws ConnectException
     */
    @Test
    public void testTogglePro() throws ConnectException {
        clickOn("#proButton");
        user = RequestManagement.getUserWithID(user.getUserId().toString());
        assertTrue(user.isPro());
        clickOn(LabeledMatchers.hasText(coinList.get(0).toString()));
        verifyThat("#supplyTag", hasText("Supply"));
        verifyThat("#vwap24HrTag", hasText("vwap24hr"));
    }

    /**
     * Tests that a user can buy a new coin and that this change is reflected in the
     * user object.
     *
     * @throws InterruptedException
     * @throws ConnectException
     */
    @Test
    public void testBuyCoin() throws InterruptedException, ConnectException {
        System.out.println("GetOwnedCoins" + user.getOwnedCoins());
        for (int i = 0; i < 4; i++) {
            clickOn("#changeAmount").type(KeyCode.BACK_SPACE, 1);
            clickOn(LabeledMatchers.hasText(coinList.get(i).toString()));
            clickOn("#changeAmount").write("2");
            clickOn("#buyButton");
            user = RequestManagement.getUserWithID(user.getUserId().toString());
            assertEquals(2.0, user.getOwnedCoins().get(coinList.get(i).getId()));
            // verifyThat("#ownCoinsListView", hasText(coinList.get(i).toString()));
        }
    }

    /**
     * Tests that a user can sell a owned coin and that this change is reflected in
     * the user object.
     *
     * @throws ConnectException
     */
    @Test
    public void testSellCoin() throws ConnectException {
        clickOn("#changeAmount").type(KeyCode.BACK_SPACE, 1);
        clickOn(LabeledMatchers.hasText(coinList.get(0).toString()));
        clickOn("#changeAmount").write("1");
        clickOn("#sellButton");
        user = RequestManagement.getUserWithID(user.getUserId().toString());
        assertTrue(user.getOwnedCoins().get(coinList.get(0).getId()) == 1.0);
    }

    /**
     * Frees mouse and keyboard for next test.
     *
     * @throws TimeoutException
     */
    @AfterEach
    public void tearDown() throws TimeoutException {

        FxToolkit.hideStage();
        release(new KeyCode[] {});
        release(new MouseButton[] {});
    }

    /**
     * Deletes all user files generated by the test.
     *
     * @throws ConnectException
     */
    @AfterAll
    public static void tearDownUser() throws ConnectException {
        try {
            RequestManagement.removeUser(testUserId, "123");
        } catch (IOException e) {
            System.out.println("Something went wrong with deleting user.");
        }
    }

}
