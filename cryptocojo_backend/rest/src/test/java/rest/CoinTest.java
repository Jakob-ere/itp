package rest;

import core.user.User;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
// import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Files;

@Import(CoinController.class)
@ContextConfiguration(classes = {
    CoinController.class,
    CryptocojoBackendApplication.class,
    CoinService.class
})
@WebMvcTest(CoinController.class)
public class CoinTest {

    /**
     * TestRestTemplate used for sending GET / POST request.
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
     * Test if you are able to get coins with userId.
     * @throws Exception
     */
    @Test
    public void getOwnedCoins() throws Exception {
        String request = Ad.getOwnedCoins + Ad.userId
                + Ad.retrievedUser.getUserId().toString();
        mockMvc.perform(get(request))
            .andExpect(status().isOk());
    }
}
