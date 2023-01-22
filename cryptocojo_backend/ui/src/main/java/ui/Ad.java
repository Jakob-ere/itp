package ui;

import java.util.Arrays;
import java.util.List;

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
    protected static final String createUserWithId = "http://localhost:8080/api/v1/user/create_user_with_id?";
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
}