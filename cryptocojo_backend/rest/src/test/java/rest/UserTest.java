package rest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.google.gson.Gson;

import core.rest.DecodeTime;
import core.user.User;
import core.user.UserAuth;

// @SpringBootTest(classes = CryptocojoBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(UserController.class)
@ContextConfiguration(classes = {
        UserController.class,
        CryptocojoBackendApplication.class,
        UserService.class
})
@WebMvcTest(UserController.class)
public class UserTest {

    @Autowired
    private MockMvc mockMvc;
 
    /**
     * Setup for the tests in this class.
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @BeforeAll
    public static void setUp() throws IllegalArgumentException, IOException {
        Ad.gson = new Gson();
        try {
            Ad.setupAuth = Files.readString(Ad.PATH_AUTH);
            Ad.setupUser = Files.readString(Ad.PATH_JSONTEST);
            Ad.setupRemoveUser = Files.readString(Ad.PATH_USERREMOVE);
        } catch (Exception e) {
            fail("Couldn't read the auth.json-file");
        }
        Ad.authUsers = Ad.gson.fromJson(Ad.setupAuth, UserAuth[].class);
        Ad.retrievedUser = Ad.gson.fromJson(Ad.setupUser, User.class);
        Ad.userRemove = Ad.gson.fromJson(Ad.setupRemoveUser, User.class);
        Ad.userOverWrite = Ad.retrievedUser;
    }

    /**
     * This is a method for retrieving a user, to check if
     * post-request does the right execution.
     * @param fileName String
     * @return
     */
    public User retrieveUserFromFile(String fileName, boolean testRemove) {
        if (testRemove) {
            if (!Files.exists(Paths.get(
                    Ad.pathUserString + fileName + ".json"))) {
                return null;
            }
        }
        try {
            Ad.userFile = Files.readString(Paths.get(
                    Ad.pathUserString + fileName + ".json"));
        } catch (Exception e) {
            fail("Could not read file");
        }
        User user1 = Ad.gson.fromJson(Ad.userFile, User.class);
        return user1;
    }

    /**
     * Tests if it gets the right User.
     * @throws Exception
     */
    @Test 
    public void testGetUser() throws Exception {
        MvcResult result = mockMvc.perform(get(Ad.getUserID + Ad.userId
                + Ad.retrievedUser.getUserId().toString()))
                .andExpect(status().isOk())
                .andReturn();
        
        String responseMessage = result.getResponse().getContentAsString();
        Ad.user = Ad.gson.fromJson(responseMessage, User.class);
        if (Ad.user.getClass() == User.class) {
            assertEquals(Ad.retrievedUser.getName(), Ad.user.getName());
            assertEquals(Ad.retrievedUser.getEmail(), Ad.user.getEmail());
        } else {
            fail("The user was not a instance of User.class");
        }
    }

    @Test
    public void testCreateUser() throws Exception {
        String request = (Ad.createUser + Ad.username + "ole-gunnar&"
                + Ad.password + "1234&"
                + Ad.email + "ola.nordmann@gmail.com");

        MvcResult result = mockMvc.perform(post(request))
                .andExpect(status().isCreated())
                .andReturn();
        String responseMessage = result.getResponse().getContentAsString();
        if (!responseMessage.isEmpty()) {
            String tmpFileName = responseMessage.split(",")[3].split(":")[1];
            Ad.stringUUID = tmpFileName.substring(1, tmpFileName.length() - 1);
            // Checks if the username is in the body.
            assertTrue(responseMessage.contains("ole-gunnar"));
            // Checks if the password is in the body.
            assertTrue(responseMessage.contains("1234"));
            // Checks if the email is in the body.
            assertTrue(responseMessage.contains("ola.nordmann@gmail.com"));
        } else {
            fail("The response does not have content.");
        }
    }

    /**
     * Tests if it returns the right request 
     * when the request is in-valid.
     * @throws Exception
     */
    @Test
    public void testCreateUserInvalidInput() throws Exception {
        mockMvc.perform(post(Ad.createUser + Ad.username + "oleG&"
                + Ad.password + "1234&" 
                + Ad.email + "ola.nordmann.gmail.com"))
                .andExpect(status().isBadRequest());
        // Existing user
        mockMvc.perform(post(Ad.createUser + Ad.username + "testExistingUser&"
                + Ad.password + "1234&" 
                + Ad.email + "ole.nordmann@gmail.com"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests if it turns the user to pro if the togglePro
     * request is sent, and vise versa if the user is already
     * pro.
     * @throws Exception
     */
    @Test
    public void testTogglePro() throws Exception {
        // Checks if the user is not pro.
        assertFalse(Ad.retrievedUser.isPro());
        mockMvc.perform(post(Ad.togglePro + Ad.userId
                + Ad.retrievedUser.getUserId().toString()))
                .andExpect(status().isOk());
        Ad.user = retrieveUserFromFile(
                Ad.retrievedUser.getUserId().toString(), false);
        // Checks if the user is pro, after toggling.
        assertTrue(Ad.user.isPro());    
        // Toggle back
        mockMvc.perform(post(Ad.togglePro + Ad.userId
                + Ad.retrievedUser.getUserId().toString()))
                .andExpect(status().isOk());
        Ad.user = retrieveUserFromFile(
                Ad.retrievedUser.getUserId().toString(), false);
        assertFalse(Ad.user.isPro());
    }

    /**
     * Tests that coins are added if valid parametres,
     * and that it sends correct response code. 
     * @throws Exception
     */
    @Test
    public void testAddValidCurrency() throws Exception {
        Ad.user = retrieveUserFromFile(
            Ad.retrievedUser.getUserId().toString(), false);
        double oldEthAmount = Ad.user.getOwnedCoins().get("bitcoin");
        
        String request = (Ad.addCurrency + Ad.userId
                + Ad.retrievedUser.getUserId().toString() + "&" + Ad.coin + "bitcoin&"
                + Ad.amount + "5.5");

        mockMvc.perform(post(request))
                .andExpect(status().isOk());
        
        Ad.user = retrieveUserFromFile(
            Ad.retrievedUser.getUserId().toString(), false);
        double newEthAmount = Ad.user.getOwnedCoins().get("bitcoin");
        if (oldEthAmount == 0) {
            assertTrue(newEthAmount == 5.5);
        }
        else {
            assertTrue(newEthAmount == oldEthAmount + 5.5);
        }
        
    }

     /**
     * Tests that coins are not sent if parametres are
     * not valid, and that it sends bad request.
     * @throws Exception
     */
    @Test
    public void testAddInvalidCurrency() throws Exception {
        // Wrong spelling
        mockMvc.perform(post(Ad.addCurrency + Ad.userId
                + Ad.retrievedUser.getUserId().toString()
                + "&" + Ad.coin + "eterium&"
                + Ad.amount + "5.5"))
                .andExpect(status().isBadRequest());
        // Negative amount
        mockMvc.perform(post(Ad.addCurrency + Ad.userId
                + Ad.retrievedUser.getUserId().toString()
                + "&" + Ad.coin + "ethereum&"
                + Ad.amount + "-5.5"))
                .andExpect(status().isBadRequest());
    }

     /**
     * Tests that coins are removed if valid parametres,
     * and that it sends correct response code. 
     * @throws Exception
     */
    @Test
    public void testRemoveValidCurrency() throws Exception {
        Ad.user = retrieveUserFromFile(
            Ad.retrievedUser.getUserId().toString(), false);
        double oldBtcAmount = Ad.user.getOwnedCoins().get("bitcoin");
        String request = Ad.removeCurrency + Ad.userId 
                + Ad.retrievedUser.getUserId().toString()
                + "&" + Ad.coin + "bitcoin&" + Ad.amount + "2.0";
        
        mockMvc.perform(post(request))
            .andExpect(status().isOk());

        Ad.user = retrieveUserFromFile(
                Ad.retrievedUser.getUserId().toString(), false);
        double newBtcAmount = Ad.user.getOwnedCoins().get("bitcoin");
        assertTrue(newBtcAmount == oldBtcAmount - 2.0);
    }

     /**
     * Tests that coins are not removed if parametres
     * are not valid, and that it sends bad request. 
     * throws Exception
     */
    @Test
    public void testRemoveInvalidCurrency() throws Exception {
        mockMvc.perform(post(Ad.removeCurrency + Ad.userId
                + Ad.retrievedUser.getUserId().toString() + "&"
                + Ad.coin + "test-coin&" + Ad.amount + "5.5"))
                .andExpect(status().isBadRequest());
        // Negative amount
        mockMvc.perform(post(Ad.removeCurrency + Ad.userId
                + Ad.retrievedUser.getUserId().toString() + "&"
                + Ad.coin + "bitcoin&" + Ad.amount + "-5.5"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that we get a list of DecodeTime, and
     * checks if we get the right time on the first element 
     * in the list.
     * @throws Exception
     */
    @Test
    public void testGetAccountInterval() throws Exception {
        String request = (Ad.getAccountInterval + Ad.timestamp + "m1&" 
                + Ad.userId + Ad.retrievedUser.getUserId().toString());

        MvcResult result = mockMvc.perform(get(request))
                .andExpect(status().isOk())
                .andReturn();

        String responseMessage = result.getResponse().getContentAsString();
        List<?> tmp = Ad.gson.fromJson(responseMessage, List.class);
        if (tmp.get(0).getClass().isInstance(DecodeTime.class)){
            DecodeTime dt = ((DecodeTime) tmp.get(0));
            // Because the timestamp is m1, the first datapoint should be 1 hour ago
            // Checks that the first datapoint has a time between 59min 30sec and 1 hr 30 sec
            assertTrue(dt.getTime().longValue() == 1668310778491L || dt.getTime().longValue() == 1667510778848L);
        }
    }

    /**
     * Tests if it can remove an existing user.
     * @throws Exception
     */
    @Test
    public void testRemoveUser() throws Exception {
        Ad.user = retrieveUserFromFile(
                Ad.userRemove.getUserId().toString(), true);
        assertEquals("testRemoveInApi", Ad.user.getName());

        String request = (Ad.removeUser + Ad.userId
                + Ad.userRemove.getUserId().toString() + "&"
                + Ad.authToken + "123");

        mockMvc.perform(delete(request))
                .andExpect(status().isOk());
        
        Ad.user = retrieveUserFromFile(
                Ad.userRemove.getUserId().toString(), true);
        if (Ad.user != null) {
            fail("The user should be removed.");
        }
    }

    /**
     * Tests if it returns the right request
     * when the request is in-valid.
     * @throws Exception
     */
    @Test
    public void testRemoveUserInvalid() throws Exception {
        // Tests if it returns a forbidden request if the authToken is invalid.
        mockMvc.perform(delete(Ad.removeUser + Ad.userId
                + Ad.userRemove.getUserId().toString()
                + "&" + Ad.authToken + "abc"))
                .andExpect(status().isForbidden());
        // Tests if it returns a NotFound request if the userId is invalid.
        mockMvc.perform(delete(Ad.removeUser + Ad.userId
                + "AnInvalidUserId&" + Ad.authToken + "123"))
                .andExpect(status().isNotFound());
    }

    /**
     * TearDown method for deleting a created user, overwriting the
     * auth.json file, overwriting the user that adds and removes currency,
     * and writing the deleted user back up again.
     */
    @AfterAll
    public static void tearDown() {
        try {
            if (!Ad.stringUUID.isEmpty()) {
                Files.delete(Paths.get(Ad.pathUserString + Ad.stringUUID + ".json"));
            }
            File dir = new File(Ad.PATH_AUTH.toString());
            Writer writer = new FileWriter(dir);
            writer.write(Ad.gson.toJson(Ad.authUsers, UserAuth[].class));
            writer.close();
            File dir2 = new File(Ad.PATH_JSONTEST.toString());
            Writer writer2 = new FileWriter(dir2);
            writer2.write(Ad.gson.toJson(Ad.userOverWrite, User.class));
            writer2.close();
            File dir3 = new File(Ad.PATH_USERREMOVE.toString());
            Writer writer3 = new FileWriter(dir3);
            writer3.write(Ad.gson.toJson(Ad.userRemove, User.class));
            writer3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 