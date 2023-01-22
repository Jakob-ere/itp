package core.rest;

import core.items.Coin;

/**
 * DecodeCoin is a data wrapper class that decodes coin data.
 */

public class DecodeCoin {

    /**
     * the label of the coin.
     */
    private String label;

    /**
     * the information about the coin.
     */
    private Coin data;

    /**
     * @return String
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return Coin
     */
    public Coin getData() {
        return data;
    }
}
