package rest;

import java.net.ConnectException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.user.User;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/login")
public class LoginController {

    /**
     * serviceclass for login.
     */
    private final LoginService loginservice;

    /**
     * controller for this class.
     *
     * @param loginserviceInput
     */
    public LoginController(final LoginService loginserviceInput) {
        this.loginservice = loginserviceInput;
    }

    /**
     * @return username, password and UUID for the user if
     *         username, password combination is legal.
     *
     * @param username username from frontend
     * @param password password from frontend
     * @throws ConnectException
     */
    @GetMapping()
    public ResponseEntity<User> getUser(
            final @RequestParam String username, final String password)
            throws ConnectException {
        try {

            User u = loginservice.retrieveUser(
                    loginservice.loginUser(username, password).getUserId());
            return new ResponseEntity<>(u, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
