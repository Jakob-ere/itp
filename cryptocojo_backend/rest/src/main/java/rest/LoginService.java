package rest;

import org.springframework.stereotype.Service;

import core.login.LoginDetails;
import core.login.LoginUser;
import core.user.GetUserFromFile;
import core.user.User;

/*
 * service class for login
 */
@Service
public class LoginService {

    /**
     * @param username - argument from frontend postmapping
     * @param password - argument from frontend postmapping
     * @return LoginDetails(username, password, UUID)
     */
    public LoginDetails loginUser(
            final String username, final String password) {
        try {
            LoginDetails details = LoginUser.loginUserFromFile(
                    username, password);
            return details;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }

    }

    /**
     * @param userId
     * @return retrieved User object
     */
    public User retrieveUser(final String userId) {
        return GetUserFromFile.retrieveUser(userId);
    }

}
