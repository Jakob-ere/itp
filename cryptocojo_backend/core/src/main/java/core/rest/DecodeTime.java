package core.rest;

/**
 * DecodeTime is a data wrapper class that transform the time in milliseconds to
 * a more readable format.
 */
public class DecodeTime {

    /**
     * time, given by a Double-value.
     */
    private Double time;

    /**
     * The price, given by a Double-value in US-dollar.
     */
    private Double priceUsd;

    /**
     * Constructor for Decodetime-class.
     * @param pointOfTime
     * @param priceInUsd
     */
    public DecodeTime(final Double pointOfTime, final Double priceInUsd) {
        this.time = pointOfTime;
        this.priceUsd = priceInUsd;
    }

    /**
     * @return Double
     */
    public Double getTime() {
        return this.time;
    }

    /**
     * @return Double
     */
    public Double getPriceUsd() {
        return this.priceUsd;
    }

    /**
     * @param amount
     */
    public void setPrice(final Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("The price must be positive!");
        }
        this.priceUsd = amount;
    }
}
