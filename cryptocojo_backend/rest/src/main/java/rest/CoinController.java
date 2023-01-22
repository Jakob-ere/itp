package rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.items.Coin;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/coin")
public class CoinController {

    /**
     * .
     * service class
     */
    private final CoinService coinservice;

    /**
     *
     * @param coinserviceInput
     */
    public CoinController(final CoinService coinserviceInput) {
        this.coinservice = coinserviceInput;
    }

    /**
     *
     * @param userId
     * @return ResponseEntity with HttpStatus and String if OK.
     */

    @GetMapping("/get_owned_coins")
    // ? "http://localhost:8080/api/v1/coin/get_owned_coins? + userId"
    public ResponseEntity<String> getOwnedCoins(
            final @RequestParam String userId) {
        try {
            String res = coinservice.getOwnedCoins(userId);
            return new ResponseEntity<String>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
