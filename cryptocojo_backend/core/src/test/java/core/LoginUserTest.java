package core;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import core.login.LoginDetails;
import core.login.LoginUser;
import core.user.SaveToFile;
import core.user.User;
import core.user.UserAuth;

/**
 * Tests LoginUser.java
 * 
 * @author Ole Dahl
 */

public class LoginUserTest {

    private static User testUser;
    private static Gson gson;
    private static String authString;
    private static Path pathUser;
    private static UserAuth[] authUsers;

    @BeforeAll
    public static void setUp() throws IllegalArgumentException, IOException {
        testUser = new User("testForLoginUser", "testForLoginUser@gmail.com", "1234");
        try {
            authString = Files.readString(SaveTest.PATH_AUTH);
        } catch (Exception e) {
            fail("Could not read the auth.json");
        }
        gson = new Gson();
        authUsers = gson.fromJson(authString, UserAuth[].class);
        SaveToFile.saveAuthInfo(testUser);
        SaveToFile.saveUserToFile(testUser);
        pathUser = Paths.get(
                System.getProperty("user.dir").replace("rest", "core")
                        + "/src/main/resources/savefiles/users/"
                        + testUser.getUserId().toString() + ".json");
    }

    /**
     * Tests that LoginUserFromFile logs in the correct user and doesn't fail
     * for invalid parametres.
     */
    @Test
    public void testLoginUserFromFile() {
        // Testing with an existing username/password combination
        LoginDetails loggedInUser = LoginUser.loginUserFromFile(
            "testForLoginUser", "1234");
        Assertions.assertEquals("testForLoginUser", loggedInUser.getUsername());
        Assertions.assertEquals("1234", loggedInUser.getPassword());
        // Testing with non-existing username/password combination
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LoginUser.loginUserFromFile(null, "1234");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LoginUser.loginUserFromFile("", "1234");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LoginUser.loginUserFromFile("panpeter", "1234");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LoginUser.loginUserFromFile("null", "123");
        });
    }

    @AfterAll
    public static void tearDown() {
        try {
            Files.delete(pathUser);
            File dir = new File(
                    System.getProperty("user.dir").replace("rest", "core")
                            + "/src/main/resources/savefiles/users/auth/auth.json");
            Writer writer = new FileWriter(dir);
            writer.write(gson.toJson(authUsers, UserAuth[].class));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
