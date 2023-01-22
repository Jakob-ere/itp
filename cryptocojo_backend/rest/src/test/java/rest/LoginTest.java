package rest;

import com.google.gson.Gson;

import core.user.User;

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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;

@Import(LoginController.class)
@ContextConfiguration(classes = {
        LoginController.class,
        CryptocojoBackendApplication.class,
        LoginService.class
})
@WebMvcTest(LoginController.class)
public class LoginTest {

    /**
     * MockMvc used for sending requests.
     */
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
            Ad.setupUser = Files.readString(Ad.PATH_JSONTEST);
        } catch (Exception e) {
            fail("Couldn't read the auth.json-file");
        }
        Ad.retrievedUser = Ad.gson.fromJson(Ad.setupUser, User.class);
    }

    /**
     * Test if you can get a user from backend with username
     * and password.
     * throws Exception
     */
    @Test
    public void getUser() throws Exception {
        String request = Ad.getUserUP +
                Ad.username + Ad.retrievedUser.getName() 
                + "&" + Ad.password + "1234";
        MvcResult response = mockMvc.perform(get(request))
            .andExpect(status().isOk())
            .andReturn();
        
        String result = response.getResponse().getContentAsString();

        assertTrue(result.contains(Ad.retrievedUser.getName()));
        assertTrue(result.contains(Ad.retrievedUser.getUserId().toString()));
    }
}
