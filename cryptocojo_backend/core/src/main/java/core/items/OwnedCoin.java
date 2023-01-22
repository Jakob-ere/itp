package core.items;

public class OwnedCoin {
    /**
     * name of a coin.
     */
    private String name;
    /**
     * amount owned of a coin.
     */
    private Double amount;

    /**
     * @param coinName   of a coin
     * @param amountOfCoin owned by the current userobject
     */
    public OwnedCoin(final String coinName, final Double amountOfCoin) {
        this.name = coinName;
        this.amount = amountOfCoin;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return amount
     */
    public Double getAmount() {
        return amount;
    }

}
