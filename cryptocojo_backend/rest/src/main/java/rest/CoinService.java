package rest;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import core.items.Coin;
import core.items.OwnedCoin;
import core.rest.GetCryptoData;
import core.user.GetUserFromFile;
import core.user.User;

@Service
public class CoinService {

    /**
     *
     * @return a collection of coin objects
     * @throws ConnectException if the method fails to retrieve data from API
     */
    public Collection<Coin> getAllAvalibleCoins() throws ConnectException {
        return GetCryptoData.getAllCoinData();
    }

    /**
     *
     * @param userId if of the user the from Controllerclass
     * @return string of all the coins a user owns and amount
     * @throws IllegalArgumentException
     */
    public String getOwnedCoins(final String userId)
            throws IllegalArgumentException, ConnectException {
        Gson gson = new Gson();
        Collection<OwnedCoin> jsonOwned = new ArrayList<OwnedCoin>();
        User user = GetUserFromFile.retrieveUser(userId);
        if (user == null) {
            throw new IllegalArgumentException();
        }
        for (String coin : user.getOwnedCoins().keySet()) {
            jsonOwned.add(new OwnedCoin(coin, user.getOwnedCoins().get(coin)));
        }
        return gson.toJson(jsonOwned);
    }
}
