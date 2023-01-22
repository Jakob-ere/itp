package core.rest;

import core.items.Coin;

import java.io.IOException;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import java.nio.charset.Charset;

/**
 * GetCryptoData contains methods for requesting different kinds of data from
 * the coincap API.
 *
 * @author Casper Andreassen
 * @author Oskar Nesheim
 * @author Jakob Relling
 *
 * @see <a href="https://docs.coincap.io/">CoinCap API reference</a>
 */

public final class GetCryptoData {

    private GetCryptoData() {
        // not called
    }

    /**
     * Constant for -40.
     */
    private static final int MINUS_FORTY = -40;
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
     * Since the URL to the coincap API is static this
     * is defined as an attribute.
     */
    private static URL url;

    /**
     * List of legal time intervals for requesting in the coincap-site.
     *
     * @return a list of timestamps.
     */
    public static List<String> getlegalTimestamps() {
        List<String> legalTimestamps = Arrays.asList("m1",
                "m5", "m15", "m30", "h1", "h2", "h6", "h12", "d1");
        return new ArrayList<String>(legalTimestamps) {

        };
    }

    /**
     * connection is a standard method for requesting data from coincap API.
     * It handles the whole request and parses the request.
     *
     * @param endpoint API endpoint to specify asset,
     *                 history or all available data.
     * @return An instance of ConnectionResponse that may or may not contain the
     *         requested data.
     */
    public static ConnectionResponse connection(final String endpoint) {
        try {
            url = new URL("https://api.coincap.io/v2/assets" + endpoint);

            HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();

            switch (code) {
                case TWO_HUNDRED:
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
                    throw new IllegalArgumentException("Something went wrong in GetCryptoData.connection");
            }

            Scanner scanner = new Scanner(url.openStream(),
                    Charset.forName("UTF-8"));
            StringBuffer buffer = new StringBuffer();
            while (scanner.hasNext()) {
                buffer.append(scanner.nextLine());
            }
            scanner.close();
            String data = buffer.toString();

            return new ConnectionResponse(TWO_HUNDRED, data);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getAllCoinData requests data about all coins on the coincap platform.
     *
     * @return A list of all available coins as Coin instances
     * @throws ConnectException if something went wrong during the request or a
     *                          bad request was sent.
     */
    public static Collection<Coin> getAllCoinData() throws ConnectException {
        ConnectionResponse data = connection("");

        if (data.getResponseCode() == TWO_HUNDRED) {
            Gson gson = new Gson();

            // Needs a Decode class to decode JSON array
            DecodeCoinManager parsed = gson.fromJson(data.getResponseMessage(),
                    DecodeCoinManager.class);
            return parsed.getData();
        } else {
            throw new ConnectException("Connection failed with status code: "
                    + data.getResponseCode()
                    + ". And response message: " + data.getResponseMessage());
        }
    }

    /**
     * getCoinData requests data about one specific coin.
     *
     * @param coinName name identifying the coin
     * @return Instance of Coin object containing data about the requested coin.
     * @throws ConnectException if something went wrong during the request or a
     *                          bad request was sent.
     */
    public static Coin getCoinData(final String coinName)
            throws ConnectException {
        ConnectionResponse data = connection("/" + coinName.toLowerCase());

        if (data.getResponseCode() == TWO_HUNDRED) {
            Gson gson = new Gson();
            DecodeCoin parsed = gson.fromJson(data.getResponseMessage(),
                    DecodeCoin.class);
            return parsed.getData();
        } else {
            throw new ConnectException("Connection failed with status code: "
                    + data.getResponseCode()
                    + ". And response message: " + data.getResponseMessage());
        }
    }

    /**
     * @param coinName  - what coin to retrieve data about
     * @param timeStamp - what timeintervall to get information about
     * @return Collection<String>
     * @throws ConnectException
     */
    public static DecodeTimeManager getHistoryData(final String coinName,
            final String timeStamp) throws ConnectException {
        return getHistoryDataWithInterVal(coinName, timeStamp,
                MINUS_FORTY, MINUS_FORTY);
    }

    /**
     * @param coinName
     * @param timeStamp
     * @param start
     * @param end
     * @return DecodeTimeManager
     * @throws ConnectException
     */
    public static DecodeTimeManager getHistoryDataWithInterVal(
            final String coinName, final String timeStamp, final long start,
            final long end) throws ConnectException {
        if (!getlegalTimestamps().contains(timeStamp)) {
            throw new IllegalArgumentException("The timestamp was invalid");
        }
        ConnectionResponse data;
        if (start != MINUS_FORTY && end != MINUS_FORTY) {
            data = connection(
                    "/" + coinName + "/history?interval=" + timeStamp
                            + "&start=" + start + "&end=" + end);
        } else {
            data = connection("/" + coinName
                    + "/history?interval=" + timeStamp);
        }

        if (data.getResponseCode() == TWO_HUNDRED) {
            DecodeTimeManager parsedTime = new Gson()
                    .fromJson(data.getResponseMessage(), DecodeTimeManager.class);
            return parsedTime;
        } else {
            throw new ConnectException("Connection failed with status code: "
                    + data.getResponseCode()
                    + ". And response message: " + data.getResponseMessage());
        }
    }
}
