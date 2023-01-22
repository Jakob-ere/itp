package core;

import core.user.User;
import core.rest.DecodeTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import static org.junit.jupiter.api.Assertions.fail;

import java.net.ConnectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

    /**
    * Test creating user, logging in, buying and selling coins.
    * @author Ole Dahl
    * @author Jakob Relling
    */

public class UserTest {

    private User user;

    private static final Path PATH_TESTJSON = Paths.get(
            System.getProperty("user.dir").replace("rest", "core")
            + "/src/main/resources/savefiles/users/d515dcf5-03a2-4f77-8434-1458eede2f4f.json");
    
    private static String userTestString;
    private static Gson gson;
    private static User userTestJson;
    private static User userExist;

    @BeforeAll
    public static void setupBeforeAll() {
        gson = new Gson();
        try {
            userTestString = Files.readString(PATH_TESTJSON);
        } catch (Exception e) {
            fail("Couldn't read the .json-file");
        }
        userTestJson = gson.fromJson(userTestString, User.class);
        userExist = userTestJson;
    }

    @BeforeEach
    public void setup() {
        user = new User("Ola Normann", "ola.normann@mail.no", "1234");
    }

    @Test
    public void testCreatInvalidUser() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new User("", "valid@gmail.com", "testUser123");
        }, "Should not be able to create a with no username");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new User("1", "valid@gmail.com", "testUser123");
        }, "Should not be able to create a user with username length less than two");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new User("testUser123", "", "testUser123");
        }, "Should not be albe to create a user with no mail.");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new User("testUser123", "testUser.gmail.com", "testUser123");
        }, "Should not be able to create a user with invalid mail-address.");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new User("testExistingUser", "testUser@gmail.com", "test");
        }, "Should not be able to create a user with the same username" 
                + " as an exsisting user");
    }

    @Test
    public void testTogglePro() {
        Assertions.assertFalse(user.isPro());
        user.togglePro();
        // Should be in pro-mode after Toggle
        Assertions.assertTrue(user.isPro());
        user.togglePro();
        // Should not be in pro-mode after Toggle
        Assertions.assertFalse(user.isPro());
    }

    /**
    * @throws ConnectException
    */
    @Test
    public void testAddCoin() throws ConnectException {
        Assertions.assertTrue(user.getOwnedCoins().isEmpty());
        user.addCurrency("bitcoin", 5.0);
        user.addCurrency("bitcoin", 10.0);
        Assertions.assertTrue(user.getOwnedCoins().containsKey("bitcoin"));
        Assertions.assertEquals(2, user.getHistory().size());

        Assertions.assertThrows(ConnectException.class, () -> {
            user.addCurrency("test-coin", 20.0);
        }, "Should not be able to add coin that does not exists.");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            user.addCurrency("bitcoin", -20.0);
        }, "Should not be able to add negative amount of a coin.");
    }

    @Test
    public void testRemoveCoin() throws ConnectException {
        userExist.removeCurrency("bitcoin", 5.0);

        Assertions.assertEquals(10.0, userExist.getOwnedCoins().get("bitcoin"));
        Assertions.assertEquals(3, userExist.getHistory().size());;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userExist.removeCurrency("bitcoin", 20.0);
        }, "Should not be able to remove an amount the user doesn't have.");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userExist.removeCurrency("bitcoin", -20.0);
        }, "Should not be able to remove a negative amount.");

        Assertions.assertThrows(ConnectException.class, () -> {
            userExist.removeCurrency("test-coin", 5.0);
        }, "Should not be able to remove a invalid coin name");
    }

    @Test
    public void testGetAccountValueOnTime() throws ConnectException {
        List<DecodeTime> lst = new ArrayList<>();
        //Throws if illegal timestamp
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            user.getAccountValueOnTime("feil");
            }, "Should throw because timeStamp is in-valid");
        //Should return empty list if nothing has been purchased
        Assertions.assertEquals(lst, user.getAccountValueOnTime("m1"));
        user.addCurrency("bitcoin", 5.0);
        //Since the list of datapoints come in different sizes, we check that the
        Assertions.assertNotEquals(0, user.getAccountValueOnTime("m1"));
        //Checking that different timestamps work properly
        Assertions.assertNotEquals(0, user.getAccountValueOnTime("m15"));
        Assertions.assertNotEquals(0, userTestJson.getAccountValueOnTime("h1"));
        Assertions.assertNotEquals(0, userTestJson.getAccountValueOnTime("h6"));
        Assertions.assertNotEquals(0, userTestJson.getAccountValueOnTime("d1"));
    }

}
