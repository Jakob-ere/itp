package core.rest;

import core.items.Coin;

import java.util.ArrayList;
import java.util.List;

/**
 * DecodeCoinManager is a data wrapper class that represents
 * multiple instances of coin objects.
 */

public class DecodeCoinManager {

    /**
     * the label of the coin.
     */
    private String label;

    /**
     * the information about the coins.
     */
    private List<Coin> data = new ArrayList<Coin>();

    /**
     * @return String
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return List<Coin>
     */
    public List<Coin> getData() {
        return new ArrayList<>(data);
    }
}
