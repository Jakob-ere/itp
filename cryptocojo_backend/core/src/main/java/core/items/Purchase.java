package core.items;

public class Purchase {
    /**
     * time at when a purchase was made.
     */
    private long time;
    /**
     * what coin was bought.
     */
    private String coin;
    /**
     * how much was bought.
     */
    private Double amount;

    /**
     * constructor.
     *
     * @param pTime  - when a purchase was made
     * @param pCoin  - what coin was bought
     * @param volume - how much was bought
     */
    public Purchase(final long pTime, final String pCoin, final Double volume) {
        this.time = pTime;
        this.coin = pCoin;
        this.amount = volume;
    }

    /**
     *
     * @return time
     */
    public long getTime() {
        return this.time;
    }

    /**
     *
     * @return amount
     */
    public String getCoin() {
        return this.coin;
    }

    /**
     *
     * @return amount
     */
    public Double getAmount() {
        return this.amount;
    }

}
