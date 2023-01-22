package rest;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import core.user.User;
import core.user.UserAuth;

/**
 * Class that store constants used in test.
 */
public class Ad {
    protected static List<String> legalTimeStamps = Arrays.asList(
            "m1", "m5", "m15",
            "m30", "h1", "h2",
            "h6", "h12", "d1");

    protected static final String baseUrl = "http://localhost:8080/api/v1/";
    protected static final String removeUser = "http://localhost:8080/api/v1/user/remove_user?";
    protected static final String getUserID = "http://localhost:8080/api/v1/user/get_user?";
    protected static final String createUser = "http://localhost:8080/api/v1/user/create_user?";
    protected static final String addCurrency = "http://localhost:8080/api/v1/user/add_currency?";
    protected static final String removeCurrency = "http://localhost:8080/api/v1/user/remove_currency?";
    protected static final String togglePro = "http://localhost:8080/api/v1/user/toggle_pro?";
    protected static final String getAccountInterval = "http://localhost:8080/api/v1/user/get_account_interval?";

    protected static final String getOwnedCoins = "http://localhost:8080/api/v1/coin/get_owned_coins?";
    protected static final String getAvailableCoins = "http://localhost:8080/api/v1/coin/get_avalible_coins?";
    protected static final String getUserUP = "http://localhost:8080/api/v1/login?";

    protected static final String userId = "userId=";
    protected static final String coin = "coin=";
    protected static final String amount = "amount=";
    protected static final String username = "username=";
    protected static final String password = "password=";
    protected static final String email = "email=";
    protected static final String timestamp = "timeStamp=";
    protected static final String authToken = "authToken=";

    protected static String testUserFile, testUserAuth, stringUUID;


    /**
     * The username that should be loaded from test.json.
     */
    protected static final String LOAD_USERNAME = "peter";

    /**
     * The mail that shoudl be loaded from test.json.
     */
    protected static final String LOAD_EMAIL = "peter.eksempel@gmail.com";

    /**
     * The username that should be in the saved file.
     */
    protected static final String SAVED_USERNAME = "ola";

    /**
     * The mail that should be in the saved file.
     */
    protected static final String SAVED_EMAIL = "ola.nordmann@gmail.com";

    protected static Gson gson;
    protected static UserAuth[] authUsers;
    protected static User user;
    protected static User userOverWrite;
    protected static User userRemove;
    protected static User retrievedUser;
    protected static String setupAuth;
    protected static String setupUser;
    protected static String setupRemoveUser;
    protected static String fileUUID;
    protected static String userFile;

    /**
     * Path-user - path to where we save user-info.
     */
    protected static String pathUserString = (System.getProperty(
            "user.dir").replace("rest", "core")
            + "/src/main/resources/savefiles/users/");

    /**
     * Path for an existing user.
     */        
    protected static final Path PATH_JSONTEST = Paths.get(pathUserString + 
            "d515dcf5-03a2-4f77-8434-1458eede2f4f.json");

    /**
     * Path for a user that should be removed in rest/UserTest.
     */        
    protected static final Path PATH_USERREMOVE = Paths.get(pathUserString + 
            "91de02c7-9c55-4a45-aeb5-9bd9189bc553.json");
    /**
     * Path-auth - path to where we save authusers.
     */
    protected static final Path PATH_AUTH = Paths.get(
            System.getProperty("user.dir").replace("rest", "core")
                    + "/src/main/resources/savefiles/users/auth/auth.json");
}