package rest;

import java.net.ConnectException;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import core.items.Coin;
import core.rest.DecodeTimeManager;
import core.rest.GetCryptoData;

/**
* @author Jakob Relling
*/
public class GetCryptoTest {

    private Coin bitcoin;
    private Collection<Coin> coins;
    private DecodeTimeManager history;
    private DecodeTimeManager historyWithInterval;

    /**
     * Constant for 100.
     */
    private static final int HUNDRED = 100;

    /**
     * Constant for 10 days in millisec.
     */
    private static final long TEN_DAYS = 86400000L * 10;

    /**
     * Time now in milliseconds.
     */
    private static long systemTime = System.currentTimeMillis();
    /**
    * Tests if we can retrieve a bitcoin from the rest-api.
    */
    @Test
    public void getCoinDataTest() {
        try {
            bitcoin = GetCryptoData.getCoinData("bitcoin");
        } catch (ConnectException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals("bitcoin", bitcoin.getId());
        Assertions.assertEquals("BTC", bitcoin.getSymbol());
        Assertions.assertThrows(ConnectException.class, () -> {
            GetCryptoData.getCoinData("bitcoiiin");
        }, "The name should be written correctly and with lowercase.");
    }

    /**
    * Tests if the responsecode is as expected.
    */
    @Test
    public void testBadRequest() {
    String response = "";
    try {
        GetCryptoData.getCoinData("test-coin");
    } catch (ConnectException e) {
        response = e.toString();
    }
    Assertions.assertEquals("java.net.ConnectException: Connection failed with status code: 404. And"
            + " response message: Could not access data about coin: /test-coin. Try again or"
            + " check spelling of coin",
            response);
    }

    /**
    * Tests if we can retrieve a 100 coins at one single request.
    */
    @Test
    public void getAllCoinDataTest() {
    try {
        coins = GetCryptoData.getAllCoinData();
    } catch (ConnectException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    Assertions.assertTrue(coins.stream().anyMatch(p ->
            p.getId().equals("bitcoin")));
    Assertions.assertEquals(HUNDRED, coins.size());
    }

    /**
    * Tests if we can retrieve history form a coin.
    */
    @Test
    public void getHistoryDataTest() {
    try {
        history = GetCryptoData.getHistoryData("bitcoin", "d1");
    } catch (ConnectException e) {
        e.printStackTrace();
    }
    Assertions.assertNotNull(history);
    Assertions.assertTrue(history.getClass().getClass().isInstance(DecodeTimeManager.class));
    Assertions.assertThrows(ConnectException.class, () -> {
        GetCryptoData.getHistoryData("bitco", "d1");
        }, "The name should be written correctly and with lowercase.");
    }

    /**
    * Tests if getHistoryData throws IllegalArgumentexception, 
    * if we give it a invalid timestamp.
    */
    @Test
    public void getHistoryDataInvalidTest() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        GetCryptoData.getHistoryData("bitcoin", "1min");
        }, "The timestamp must be valid.");
    }

    /**
    * Tests if we can retrieve history with interval fromm a coin.
    */
    @Test
    public void getHistoryDataWithIntervalTest() {
    try {
        historyWithInterval = GetCryptoData.getHistoryDataWithInterVal("bitcoin", "d1", systemTime - TEN_DAYS, systemTime);
    } catch (ConnectException e) {
        e.printStackTrace();
    }
    Assertions.assertNotNull(historyWithInterval);
    Assertions.assertTrue(historyWithInterval.getClass().getClass().isInstance(DecodeTimeManager.class));
    Assertions.assertThrows(ConnectException.class, () -> {
        GetCryptoData.getHistoryData("bitco", "d1");
        }, "The name should be written correctly and with lowercase.");
    }

    /**
    * Tests if getHistoryDataWith interval throws the right exceptions.
    */
    @Test
    public void getHistoryDataIntervalInvalidTest() {
    Assertions.assertThrows(ConnectException.class, () -> {
        GetCryptoData.getHistoryDataWithInterVal("bitcoin",
                "d1", systemTime , systemTime -100000L);
        }, "The start-time should be less than end-time.");
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        GetCryptoData.getHistoryDataWithInterVal("bitcoin",
                "day", systemTime - TEN_DAYS, systemTime);
        }, "The timestamp must be valid.");
    Assertions.assertThrows(ConnectException.class, () -> {
        GetCryptoData.getHistoryDataWithInterVal("bitcoin", "d1", 0, systemTime);
        }, "The start-time must be be between today and"
            +" eleven years in the past.");
    }
}
