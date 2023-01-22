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
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import core.user.GetUserFromFile;
import core.user.SaveToFile;
import core.user.User;
import core.user.UserAuth;

/**
 * Test for saving and retrieving users
 *
 * @author Jakob Relling
 */

public class SaveTest {

    private String testUserFile, testUserAuth;

    /**
     * The username that should be loaded from test.json.
     */
    private final String LOAD_USERNAME = "testUserForApi";

    /**
     * The mail that shoudl be loaded from test.json.
     */
    private final String LOAD_EMAIL = "testUser.ForApi@gmail.com";

    /**
     * The username that should be in the saved file.
     */
    private final String SAVED_USERNAME = "olaNordmann";

    /**
     * The mail that should be in the saved file.
     */
    private final String SAVED_EMAIL = "ola.nordmann@gmail.com";

    private static UserAuth[] authUsers;
    private static Gson parser;
    private static User user;
    private static String setupAuthString;

    /**
     * Path-user - path to where we save user-info.
     */
    private static Path pathUser;

    /**
     * Path-auth - path to where we save authusers.
     */
    protected static final Path PATH_AUTH = Paths.get(
            System.getProperty("user.dir").replace("rest", "core")
                    + "/src/main/resources/savefiles/users/auth/auth.json");

    /**
     * Makes two paths, the first one is for where our new User is going to be
     * made.
     * The second
     * one is where our authusers is saved. We then retrieve all the authusers so
     * that we can
     * write the authfile again in the teardown method.
     */
    @BeforeAll
    public static void setup() {
        user = new User("olaNordmann", "ola.nordmann@gmail.com", "1234");
        pathUser = Paths.get(System.getProperty("user.dir").replace("rest", "core")
                + "/src/main/resources/savefiles/users/"
                + user.getUserId().toString() + ".json");
        try {
            setupAuthString = Files.readString(PATH_AUTH);
        } catch (Exception e) {
            fail("Couldn't read the auth.json-file");
        }
        parser = new Gson();
        authUsers = parser.fromJson(setupAuthString, UserAuth[].class);
    }

    /**
     * Tests if we can read from an already created test.json file
     * 
     * @throws Exception
     * @throws JsonIOException
     * @throws JsonSyntaxException
     */
    @Test
    public void testLoadUser() throws JsonSyntaxException, JsonIOException, Exception {
        User user2 = GetUserFromFile.retrieveUser("d515dcf5-03a2-4f77-8434-1458eede2f4f");
        if (user2 == null) {
            fail("Couldn't create a user with the test.json-file");
        }
        Assertions.assertEquals(user2.getName(), LOAD_USERNAME);
        Assertions.assertEquals(user2.getEmail(), LOAD_EMAIL);
    }

    /**
     * Tests if we can save a user to a json.file. We then use a Files-labrary to
     * read the file, and tests if the user was saved correctly.
     * 
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testSavingUser() throws IllegalArgumentException, IOException {
        SaveToFile.saveUserToFile(user);
        try {
            testUserFile = Files.readString(pathUser);
        } catch (Exception e) {
            fail("Couldn't read the new .json-file");
        }
        User userFromFile = parser.fromJson(testUserFile, User.class);
        Assertions.assertEquals(userFromFile.getName(), SAVED_USERNAME);
        Assertions.assertEquals(userFromFile.getEmail(), SAVED_EMAIL);
    }

    /**
     * Tests if we can save a userauth to a json.file. We then use a Files-labrary
     * to
     * read the file, and tests if the userauth was saved correctly.
     * 
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testSaveAuthInfo() throws IllegalArgumentException, IOException {
        SaveToFile.saveAuthInfo(user);
        try {
            testUserAuth = Files.readString(PATH_AUTH);
        } catch (Exception e) {
            fail("Couldn't read the auth.json-file");
        }
        UserAuth[] authUsersSaved = parser.fromJson(testUserAuth, UserAuth[].class);
        Assertions.assertTrue(authUsers.length < authUsersSaved.length);
    }

    /**
     * Tests if SaveToFile-class is throwing IllegalArgumentException
     * if the user/authuser is null
     */
    @Test
    public void testSaveNonExistingUser() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SaveToFile.saveUserToFile(null);
        }, "The user should not be equal to null");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SaveToFile.saveAuthInfo(null);
        }, "The user should not be equal to null");
    }

    /**
     * It deletes the new user-file aswell as overwriting the auth-file with
     * the original authusers.
     */
    @AfterAll
    public static void tearDown() {
        try {
            Files.delete(pathUser);
            File dir = new File(
                    System.getProperty("user.dir").replace("rest", "core")
                            + "/src/main/resources/savefiles/users/auth/auth.json");
            Writer writer = new FileWriter(dir);
            writer.write(parser.toJson(authUsers, UserAuth[].class));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
