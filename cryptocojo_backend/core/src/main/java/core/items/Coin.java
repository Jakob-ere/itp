package core.items;

/**
 * <p>
 * Coin is a data container class for representing a coin.
 * </p>
 *
 * @author Casper Andreassen
 */

public class Coin {

    /**
     * id - id for coin.
     */
    private String id;

    /**
     * rank - rank of based on marketcap from CoinCap API.
     */
    private String rank;

    /**
     * name - name of the coin.
     */
    private String name;
    /**
     * symbol - ticker from the coin.
     */
    private String symbol;

    /**
     * supply - how many coins are in circulation.
     */
    private Double supply;

    /**
     * maxSupply - how many coins there are total.
     */
    private Double maxSupply;

    /**
     * marketCapUsd - how much the supply is worth in USD.
     */
    private Double marketCapUsd;

    /**
     * volueUsd24Hr - how much has been sold/bought the last 24 hours.
     */
    private Double volumeUsd24Hr;

    /**
     * priceUsd - price of a single coin in USD.
     */
    private Double priceUsd;

    /**
     * changePercent24Hr - change of coinprice in percentage.
     */
    private Double changePercent24Hr;

    /**
     * vwap24Hr - volume in USD divided by number of
     * trades in the last 24 hours.
     */
    private Double vwap24Hr;

    /**
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * @return String
     */
    public String getRank() {
        return rank;
    }

    /**
     * @return String
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * @return Double
     */
    public Double getSupply() {
        return supply;
    }

    /**
     * @return String
     */
    public String prettySupply() {
        return PriceFormat.numberFormat(getSupply());
    }

    /**
     * @return Double
     */
    public Double getMaxSupply() {
        return maxSupply;
    }

    /**
     * @return Double
     */
    public Double getMarketCapUsd() {
        return marketCapUsd;
    }

    /**
     * @return String
     */
    public String prettyMarketCapUsd() {
        return PriceFormat.numberFormat(getMarketCapUsd());
    }

    /**
     * @return Double
     */
    public Double getVolumeUsd24Hr() {
        return volumeUsd24Hr;
    }

    /**
     * @return String
     */
    public String prettyVolumeUsd24Hr() {
        Double volume = getVolumeUsd24Hr();
        return PriceFormat.numberFormat(volume);
    }

    /**
     * @return Double
     */
    public Double getPriceUsd() {
        return priceUsd;
    }

    /**
     * @return String
     */
    public String prettyPriceUsd() {
        return PriceFormat.priceNumberFormat(getPriceUsd());
    }

    /**
     * @return Double
     */
    public Double getChangePercent24Hr() {
        return changePercent24Hr;
    }

    /**
     * @return Double
     */
    public Double getVwap24Hr() {
        return vwap24Hr;
    }

    /**
     * @return String
     */
    public String prettyVwap24hr() {
        return PriceFormat.priceNumberFormat(getVwap24Hr());
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return getName() + " - " + prettyPriceUsd();
    }
}
