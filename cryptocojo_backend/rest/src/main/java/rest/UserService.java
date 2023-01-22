package rest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.ConnectException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import core.rest.DecodeTime;
import core.user.GetUserFromFile;
import core.user.SaveToFile;
import core.user.User;
import core.user.UserAuth;

/*
 * service class for user controller
 */
@Service
public class UserService {

    /**
     * Path for where the AuthUsers are stored.
     */
    private static final String AUTH_PATH = (System.getProperty(
            "user.dir").replace("rest", "core")
            + "/src/main/resources/savefiles/users/auth/auth.json");

    /**
     * Path for where the users are stored.
     */
    private static final String USER_PATH = (System.getProperty(
            "user.dir").replace("rest", "core")
            + "/src/main/resources/savefiles/users/");

    /**
     * @param userId id of the user that will get retrieved
     * @return User - if user object is found, it will be returned
     */
    public User getUser(final String userId) {
        return GetUserFromFile.retrieveUser(userId);
    }

    /**
     * @param username username for new user object
     * @param password password for new user object
     * @param email    email for new user object
     * @param userId   UUID for the new user object
     * @return User - the new user object.
     * @throws IOException
     */
    public User createUser(
            final String username,
            final String password,
            final String email,
            final String userId) throws IOException {
        try {
            User user;
            if (userId == null) {
                user = new User(username, email, password);
            } else {
                user = new User(username, email, password, userId);
            }
            SaveToFile.saveAuthInfo(user);
            SaveToFile.saveUserToFile(user);
            System.out.println("UserService.createNewUser.user--------" + user);
            return user;
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * @param user
     * @param timeStamp
     * @return List<DecodeTime> which represents account value over time
     * @throws ConnectException
     */
    public List<DecodeTime> getAccountValueOnTime(final User user,
            final String timeStamp) throws ConnectException {
        return user.getAccountValueOnTime(timeStamp);
    }

    /**
     * Method to remove user from our storage.
     *
     * @param userId
     * @param authToken
     * @return boolean.
     * @throws IOException
     */
    public boolean removeUser(final String userId,
            final String authToken) throws IOException {
        Gson parser = new Gson();
        String setupAuthString = Files.readString(Paths.get(AUTH_PATH));
        UserAuth[] authUsers = parser.fromJson(
                setupAuthString, UserAuth[].class);
        ArrayList<UserAuth> authUsersWithout = new ArrayList<UserAuth>();
        for (UserAuth user : authUsers) {
            if (!user.getUserId().equals(userId)) {
                authUsersWithout.add(user);
            }
        }
        File dir = new File(AUTH_PATH);
        Writer writer = new FileWriter(dir, Charset.forName("UTF-8"));
        try {
            writer.write(parser.toJson(authUsersWithout.toArray(),
                    UserAuth[].class));
            writer.close();
            if (!Files.exists(Paths.get(USER_PATH + userId + ".json"))) {
                return false;
            }
            Files.delete(Paths.get(USER_PATH + userId + ".json"));
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        } finally {
            writer.close();
        }
    }

    /**
     * @param user
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public void saveUserToFile(final User user)
            throws IllegalArgumentException, IOException {
        SaveToFile.saveUserToFile(user);
    }

    /**
     * @param user
     * @param coin
     * @param amount
     * @throws ConnectException
     */
    public void addCurrency(final User user,
            final String coin, final Double amount)
            throws ConnectException {
        user.addCurrency(coin, amount);
    }

    /**
     * @param user
     * @param coin
     * @param amount
     * @throws ConnectException
     */
    public void removeCurrency(final User user,
            final String coin, final Double amount)
            throws ConnectException, IllegalArgumentException {
        user.removeCurrency(coin, amount);
    }

    /**
     * @param user
     */
    public void togglePro(final User user) {
        user.togglePro();
    }

}
