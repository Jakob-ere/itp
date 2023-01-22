package ui;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import com.google.gson.Gson;

import core.items.Coin;
import core.items.OwnedCoin;
import core.rest.ConnectionResponse;
import core.rest.DecodeCoinManager;
import core.user.User;

public class RequestManagement {

    /**
     * Constant for 200.
     */
    private static final int TWO_HUNDRED = 200;

    /**
     * Constant for 400.
     */
    private static final int FOUR_HUNDRED = 400;

    /**
     * Constant for 401.
     */
    private static final int FOUR_O_ONE = 401;

    /**
     * Constant for 404.
     */
    private static final int FOUR_O_FOUR = 404;

    /**
     * Constant for 429.
     */
    private static final int FOUR_TWENTY_NINE = 429;

    /**
     * Constant for 500.
     */
    private static final int FIVE_HUNDRED = 500;

    /**
     * Constant for 201.
     */
    private static final int TWO_O_ONE = 201;

    /**
     * @param endpoint
     * @param method
     * @param noReturn
     * @return ConnectionResponse object(statuscode, data)
     */
    public static ConnectionResponse request(final String endpoint, final String method, final Boolean noReturn) {
        URL url;
        try {
            url = new URL(endpoint);

            HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
            connection.setRequestMethod(method);
            connection.connect();

            int code = connection.getResponseCode();
            switch (code) {
                case TWO_HUNDRED:
                    break;
                case TWO_O_ONE:
                    break;
                case FOUR_HUNDRED:
                    return new ConnectionResponse(FOUR_HUNDRED, "Bad request");
                case FOUR_O_ONE:
                    return new ConnectionResponse(FOUR_O_ONE, "Unauthorized");
                case FOUR_O_FOUR:
                    return new ConnectionResponse(FOUR_O_FOUR,
                            "Could not access data about coin: " + endpoint
                                    + ". Try again or check spelling of coin");
                case FOUR_TWENTY_NINE:
                    return new ConnectionResponse(FOUR_TWENTY_NINE,
                            "Rate limit exceeded of"
                                    + " 100 requests pr minute, wait up to 1 minute");
                case FIVE_HUNDRED:
                    return new ConnectionResponse(FIVE_HUNDRED,
                            "Internal server error");
                default:
                    throw new IllegalArgumentException("Something went wrong in ConnectionResponse.request!");
            }

            if (noReturn) {
                return new ConnectionResponse(code, "");
            }
            Scanner scanner = new Scanner(url.openStream(),
                    Charset.forName("UTF-8"));
            StringBuffer buffer = new StringBuffer();
            while (scanner.hasNext()) {
                buffer.append(scanner.nextLine());
            }
            scanner.close();
            String data = buffer.toString();

            return new ConnectionResponse(code, data);
        } catch (IOException e) {
            System.out.println("Connection failed");
        }
        return null;
    }

    /**
     * Method for getting a user with username / password.
     *
     * @param username String
     * @param password String
     * @return String - json format of user
     * @throws ConnectException
     */
    public static User getUserWithUP(
            final String username,
            final String password) throws ConnectException {
        String request = Ad.getUserUP +
                Ad.username + username + "&" +
                Ad.password + password;

        ConnectionResponse response = RequestManagement
                .request(request, "GET", false);
        if (response.getResponseCode() != 200) {
            throw new ConnectException("Connection failed with status code: "
                    + response.getResponseCode()
                    + ". And response message: " +
                    response.getResponseMessage());
        }
        return createUserFromJSON(response.getResponseMessage());
    }

    /**
     * Method for all coins that a user owns.
     *
     * @param userId String
     * @return String - json format of all coin to a user
     * @throws ConnectException
     */
    public static Collection<OwnedCoin> getOwnedCoins(final String userId) throws ConnectException {
        String request = Ad.getOwnedCoins + Ad.userId + userId;
        ConnectionResponse response = RequestManagement.request(request, "GET", false);
        if (response.getResponseCode() != 200) {
            throw new ConnectException("Connection failed with status code: "
                    + response.getResponseCode()
                    + ". And response message: " + response.getResponseMessage());
        }
        return createOwnedCoinsFromJSON(response.getResponseMessage());
    }

    /**
     * Get a user with userId.
     *
     * @param userId String
     * @return String - json format of user
     * @throws ConnectException
     */
    public static User getUserWithID(final String userId) throws ConnectException {
        String request = Ad.getUserID + Ad.userId + userId;
        ConnectionResponse response = RequestManagement.request(request, "GET", false);
        if (response.getResponseCode() != 200) {
            throw new ConnectException("Connection failed with status code: "
                    + response.getResponseCode()
                    + ". And response message: " + response.getResponseMessage());
        }
        return createUserFromJSON(response.getResponseMessage());
    }

    /**
     * Method for creating a new user.
     *
     * @param username String
     * @param password String
     * @param email    String
     * @return String - json - user
     * @throws ConnectException
     */
    public static User createNewUser(
            final String username,
            final String password,
            final String email) throws ConnectException {
        String request = Ad.createUser +
                Ad.username + username + "&" +
                Ad.password + password + "&" +
                Ad.email + email;
        RequestManagement.request(request, "POST", false);
        return RequestManagement.getUserWithUP(username, password);
    }

    /**
     * Method for creating a new user.
     *
     * @param username String
     * @param password String
     * @param email    String
     * @return String - json - user
     * @throws ConnectException
     */
    public static User createNewUserWithId(
            final String username,
            final String password,
            final String email,
            final String userId) throws ConnectException {
        String request = Ad.createUserWithId +
                Ad.username + username + "&" +
                Ad.password + password + "&" +
                Ad.email + email + "&userId=" + userId;
        RequestManagement.request(request, "POST", false);
        return RequestManagement.getUserWithUP(username, password);
    }

    /**
     * Method for turning on/off pro mode.
     *
     * @param userId String
     * @return void
     * @throws ConnectException
     */
    public static void togglePro(
            final String userId) throws ConnectException {
        String r = Ad.togglePro + Ad.userId + userId;
        ConnectionResponse response = RequestManagement.request(r, "POST", true);
        if (response.getResponseCode() != 200) {
            throw new ConnectException("Connection failed with status code: "
                    + response.getResponseCode()
                    + ". And response message: " + response.getResponseMessage());
        }
    }

    /**
     * Method for buying coins.
     *
     * @param userId String
     * @param coin   String
     * @param amount String
     * @return
     * @throws ConnectException
     */
    public static void addCurrency(
            final String userId,
            final String coin,
            final String amount) throws ConnectException {
        String r = Ad.addCurrency + Ad.userId + userId + "&"
                + Ad.coin + coin + "&"
                + Ad.amount + amount;
        ConnectionResponse response = RequestManagement.request(r, "POST", true);
        if (response.getResponseCode() != 200) {
            throw new ConnectException("Connection failed with status code: "
                    + response.getResponseCode()
                    + ". And response message: " + response.getResponseMessage());
        }
    }

    /**
     * Method for selling coins.
     *
     * @param userId String
     * @param coin   String
     * @param amount String
     * @return
     * @throws ConnectException
     */
    public static void removeCurrency(
            final String userId,
            final String coin,
            final String amount) throws ConnectException {
        String r = Ad.removeCurrency + Ad.userId + userId + "&" +
                Ad.coin + coin + "&" +
                Ad.amount + amount;
        ConnectionResponse response = RequestManagement.request(r, "POST", true);
        if (response.getResponseCode() != 200) {
            throw new ConnectException("Connection failed with status code: "
                    + response.getResponseCode()
                    + ". And response message: " + response.getResponseMessage());
        }
    }

    /**
     * @param userId
     * @param authToken
     * @return
     * @throws ConnectException
     */
    public static void removeUser(
            final String userId,
            final String authToken) throws ConnectException {
        String request = Ad.removeUser + Ad.userId + userId + "&" +
                Ad.authToken + authToken;
        RequestManagement.request(request, "DELETE", false);
    }

    /**
     * This methods return all available coins from CoinCap.
     *
     * @return
     */
    public static Collection<Coin> getAllCoins() {
        Gson gson = new Gson();
        String request = "https://api.coincap.io/v2/assets";

        DecodeCoinManager coins = gson.fromJson(
                RequestManagement.request(request, "GET", false).getResponseMessage(), DecodeCoinManager.class);
        return coins.getData();
    }

    /**
     * Creates a user from input.
     *
     * @param data String - data from REST API
     * @return
     */
    private static User createUserFromJSON(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, User.class);
    }

    /**
     * Method for converting string to Collection of OwnedCoin.
     *
     * @param data String
     * @return Collection of OwnedCoin
     */
    private static Collection<OwnedCoin> createOwnedCoinsFromJSON(String data) {
        Gson gson = new Gson();
        return new ArrayList<>(
                Arrays.asList(
                        gson.fromJson(data, OwnedCoin[].class)));
    }

}
