package rest;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.rest.DecodeTime;
import core.user.User;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    /**
     * service class.
     */
    private final UserService userservice;

    /**
     * controller for this class.
     *
     * @param userserviceInput
     */
    public UserController(final UserService userserviceInput) {
        this.userservice = new UserService();
    }

    /**
     * @param userId - input from frontend
     * @return ResponseEntity<User> - User ojbect as well as HttpStatus
     */
    @GetMapping("/get_user")
    public ResponseEntity<User> getUser(final @RequestParam String userId) {
        User foundUser = userservice.getUser(userId);

        return foundUser == null ? new ResponseEntity<>(
                HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    /**
     *
     * @param timeStamp
     * @param userId
     * @return a list of Decode time objects and HttpStatus
     * @throws ConnectException
     */
    @GetMapping("/get_account_interval")
    public ResponseEntity<List<DecodeTime>> getAccountInterval(
            final @RequestParam String timeStamp, final String userId)
            throws ConnectException {
        User foundUser = userservice.getUser(userId);
        List<DecodeTime> foundList = userservice.getAccountValueOnTime(
                foundUser, timeStamp);
        return foundList == null ? new ResponseEntity<>(
                HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(foundList, HttpStatus.OK);
    }

    /**
     * @param username input from frontend
     * @param password input from frontend
     * @param email    input from frontend
     * @param userId   input from frontend
     * @return ResponseEntity<User> - User object as well as HttpStatus
     * @throws Exception
     * @throws ServerException
     */
    @PostMapping("/create_user")
    public ResponseEntity<User> createNewUser(
            final @RequestParam String username,
            final @RequestParam String password,
            final @RequestParam String email) throws Exception {
        User user = userservice.createUser(username, password, email, null);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param username input from frontend
     * @param password input from frontend
     * @param email    input from frontend
     * @param userId   input from frontend
     * @return ResponseEntity<User> - User object as well as HttpStatus
     * @throws Exception
     * @throws ServerException
     */
    @PostMapping("/create_user_with_id")
    public ResponseEntity<User> createNewUser(
            final @RequestParam String username,
            final @RequestParam String password,
            final @RequestParam String email,
            final @RequestParam String userId) throws Exception {
        User user = userservice.createUser(username, password, email, userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param userId id of the user that wants to change their pro status
     * @return HttpStatus for the post
     * @throws Exception is the user is not found
     */
    @PostMapping("/toggle_pro")
    public ResponseEntity<Void> togglePro(
            final @RequestParam String userId) throws Exception {
        User foundUser = userservice.getUser(userId);
        if (foundUser != null) {
            userservice.togglePro(foundUser);
            userservice.saveUserToFile(foundUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // throw new ServerException("Cannot create user");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param userId - input from frontend
     * @param coin   - input from frontend
     * @param amount - input from frontend
     * @return ResponseEntity<Void> - HttpStatus
     */
    @PostMapping("/add_currency")
    public ResponseEntity<Void> addCurrency(
            final @RequestParam String userId,
            final String coin,
            final String amount) {

        Double value = Double.valueOf(amount);
        User foundUser = userservice.getUser(userId);
        try {
            userservice.addCurrency(foundUser, coin, value);
            userservice.saveUserToFile(foundUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param userId - input from frontend
     * @param coin   - input from frontend
     * @param amount - input from frontend
     * @return ResponseEntity<Void> - HttpStatus
     */
    @PostMapping("/remove_currency")
    public ResponseEntity<Void> removeCurrency(
            final @RequestParam String userId,
            final String coin,
            final String amount) {
        Double value = Double.valueOf(amount);
        User foundUser = userservice.getUser(userId);
        try {
            userservice.removeCurrency(foundUser, coin, value);
            userservice.saveUserToFile(foundUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a user from our local storage.
     *
     * @param userId    String
     * @param authToken String
     * @return String - userId of the deleted user.
     * @throws IOException
     */
    @DeleteMapping("/remove_user")
    public ResponseEntity<String> removeUser(
            final @RequestParam String userId,
            final String authToken)
            throws IOException {
        if (authToken.equals("123")) {
            boolean res = userservice.removeUser(userId, authToken);
            if (res) {
                return new ResponseEntity<String>(userId, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(userId, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<String>(userId, HttpStatus.FORBIDDEN);
        }
    }

}
