package core.user;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import core.items.Purchase;
import core.rest.DecodeTime;
import core.rest.GetCryptoData;

/**
 * @author Ole Dahl
 * @author Casper Salminen
 * @author Jakob Relling
 */

public class User {

    /**
     * name - name of the user object.
     */
    private String name;

    /**
     * email - email of user object.
     */
    private String email;

    /**
     * password - password of the user object.
     */
    private String password;

    /**
     * userId - ID of the user object.
     */
    private UUID userId;

    /**
     * Negative_Index to see if new index is positive.
     */
    private static final int NEGATIVE_INDEX = -1;

    /**
     * A day given in milliseconds.
     */
    private static final long DAY_IN_MILLIS = 86400000L;

    /**
     * 12 Hours in milliseconds.
     */
    private static final long TWELVE_H = DAY_IN_MILLIS / 2;

    /**
     * 6 Hours in milliseconds.
     */
    private static final long SIX_H = DAY_IN_MILLIS / 4;

    /**
     * 2 days in milliseconds.
     */
    private static final long TWO_DAYS = DAY_IN_MILLIS * 2;

    /**
     * 4 days in milliseconds.
     */
    private static final long FOUR_DAYS = DAY_IN_MILLIS * 4;

    /**
     * 8 days in milliseconds.
     */
    private static final long EIGHT_DAYS = DAY_IN_MILLIS * 8;

    /**
     * 15 days in milliseconds.
     */
    private static final long FIFTEEN_DAYS = DAY_IN_MILLIS * 15;

    /**
     * 30 days in milliseconds.
     */
    private static final long THIRTY_DAYS = DAY_IN_MILLIS * 30;

    /**
     * pro - version which lets the user get more information in the GUI app.
     */
    private boolean pro;

    /**
     * ownedCoins - all the coins the user owns.
     */
    private Map<String, Double> ownedCoins = new HashMap<String, Double>();

    /**
     * buyHistory - List of all the purchases.
     */
    private List<Purchase> buyHistory = new ArrayList<Purchase>();

    /**
     * Creates a new user when user does not already exist.
     * Generates a random UUID.
     *
     * @param username
     * @param mail
     * @param userPassword
     */
    public User(final String username, final String mail,
            final String userPassword) throws IllegalArgumentException {
        if (!username.isEmpty()) {
            if (validUsername(username)) {
                this.name = username;
            }
        } else {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        if (!mail.isEmpty() && !userPassword.isEmpty()) {
            if (!validEmail(mail)) {
                throw new IllegalArgumentException("The email-address"
                        + " is not valid!");
            }
            this.email = mail;
            this.password = userPassword;
        } else {
            throw new IllegalArgumentException("Email cannot be empty!");
        }
        this.userId = UUID.randomUUID();
        this.pro = false;
    }

    /**
     * Constructor.
     *
     * @param username
     * @param mail
     * @param userPassword
     * @param userIdInput
     */
    public User(final String username, final String mail,
            final String userPassword, final String userIdInput) {
        this(username, mail, userPassword);
        this.userId = UUID.fromString(userIdInput);
    }

    /**
     * @return List<Purchase>
     */
    public List<Purchase> getHistory() {
        return new ArrayList<>(this.buyHistory);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "User [email=" + email + ", name="
                + name + ", ownedCoins=" + ownedCoins + "]";
    }

    /**
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return UUID
     */
    public UUID getUserId() {
        return this.userId;
    }

    /**
     * @return boolean
     */
    public boolean isPro() {
        return this.pro;
    }

    /**
     * Turning pro from true to false and vise versa.
     */
    public void togglePro() {
        this.pro = !this.pro;
    }

    /**
     * @return Map with string representation of coin as key, and double
     *         representation of amount as value.
     */
    public Map<String, Double> getOwnedCoins() {
        return new HashMap<>(this.ownedCoins);
    }

    /**
     * @return String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Method for adding a coin to the user´s holdings.
     *
     * @param type   String representation of coin.
     * @param amount Double representation of amount.
     * @throws ConnectException
     * @throws IllegalArgumentException() if coin is unavailable or if the user
     *                                    tries to add a negative amount.
     */
    public void addCurrency(final String type, final Double amount)
            throws ConnectException {
        String validCoin = GetCryptoData.getCoinData(type).getId();
        if (amount < 0.0) {
            throw new IllegalArgumentException("Cannot add negative "
                    + "amount of a coin");
        }
        if (this.ownedCoins.containsKey(validCoin)) {
            this.ownedCoins.put(validCoin,
                    this.ownedCoins.get(validCoin) + amount);
        } else {
            this.ownedCoins.put(validCoin, amount);
        }
        this.buyHistory.add(new Purchase(System.currentTimeMillis(),
                validCoin, amount));
    }

    /**
     * Method for removing an amount of a coin from the user´s holdings.
     *
     * @param type   String representation of coin.
     * @param amount Double representation of amount.
     * @throws ConnectException
     * @throws IllegalArgumentException() if the user tries to remove more than
     *                                    is in the account.
     */
    public void removeCurrency(final String type, final Double amount)
            throws ConnectException {
        String validCoin = GetCryptoData.getCoinData(type).getId();

        if (amount < 0.0) {
            throw new IllegalArgumentException("Cannot add negative "
                    + "amount of a coin");
        }
        if (this.ownedCoins.get(validCoin) < amount) {
            throw new IllegalArgumentException("Cannot remove more "
                    + validCoin + " than is in account");
        }
        if (this.ownedCoins.get(validCoin).equals(amount)) {
            this.ownedCoins.remove(validCoin);
        } else {
            this.ownedCoins.put(validCoin,
                    this.ownedCoins.get(validCoin) - amount);
        }
        this.buyHistory.add(new Purchase(System.currentTimeMillis(),
                validCoin, -amount));
    }

    /**
     * Method to get a user portfolio, so each user can check their
     * account history.
     *
     * @param timestamp
     * @return List<DecodeTime> with portfolio value over an interval
     *         specified by timestamp.
     * @throws ConnectException
     */
    public List<DecodeTime> getAccountValueOnTime(final String timestamp)
            throws ConnectException {
        long currentTime = System.currentTimeMillis();
        long startTime = currentTime;

        switch (timestamp) {
            case "m1":
                startTime = currentTime - SIX_H;
                break;
            case "m15":
                startTime = currentTime - TWELVE_H;
                break;
            case "m30":
                startTime = currentTime - DAY_IN_MILLIS;
                break;
            case "h1":
                startTime = currentTime - TWO_DAYS;
                break;
            case "h2":
                startTime = currentTime - FOUR_DAYS;
                break;
            case "h6":
                startTime = currentTime - EIGHT_DAYS;
                break;
            case "h12":
                startTime = currentTime - FIFTEEN_DAYS;
                break;
            case "d1":
                startTime = currentTime - THIRTY_DAYS;
                break;
            default:
                throw new IllegalArgumentException("Illegal timestamp");
        }
        List<DecodeTime> lstDecodeTime;
        List<DecodeTime> finished = new ArrayList<>();
        int index = 0;
        for (Purchase buy : buyHistory) {
            lstDecodeTime = GetCryptoData.getHistoryDataWithInterVal(
                    buy.getCoin().toLowerCase(),
                    timestamp, startTime, currentTime).getData();
            if (index == 0) {
                for (DecodeTime iter : lstDecodeTime) {
                    finished.add(new DecodeTime(iter.getTime(), 0.0));
                }
            }
            if (buy.getTime() < startTime) {
                for (int i = 0; i < lstDecodeTime.size()
                        && i < finished.size(); i++) {
                    finished.get(i).setPrice(
                            finished.get(i).getPriceUsd()
                                    + (lstDecodeTime.get(i).getPriceUsd()
                                            * buy.getAmount()));
                }
            } else {
                int validIndex = NEGATIVE_INDEX;
                for (DecodeTime decodeTime : finished) {
                    if (buy.getTime() <= decodeTime.getTime()) {
                        validIndex = finished.indexOf(decodeTime);
                        break;
                    }
                }
                if (validIndex != NEGATIVE_INDEX) {
                    while (validIndex < finished.size()
                            && validIndex < lstDecodeTime.size()) {
                        finished.get(validIndex).setPrice(
                                finished.get(validIndex).getPriceUsd()
                                        + (lstDecodeTime.get(validIndex).getPriceUsd()
                                                * buy.getAmount()));
                        validIndex++;
                    }
                }
            }
            index++;
        }
        return finished;
    }

    /**
     * Returns true if the username is unique for the current users.
     *
     * @param mail
     * @return boolean
     */
    public boolean validEmail(final String mail) {
        return UserValidation.checkIfValidEmail(mail);
    }

    /**
     * Returns true if the username is unique for the current users.
     *
     * @param username
     * @return boolean
     */
    public boolean validUsername(final String username) {
        return UserValidation.checkIfValidUsername(username);
    }
}
