package core.login;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class LoginUser {

    private LoginUser() {
        // not called
    }

    /**
     * Method to login a user from a file.
     *
     * @param username
     * @param password
     * @return User
     */
    public static LoginDetails loginUserFromFile(final String username,
            final String password) throws IllegalArgumentException {
        if (username == null || password == null) {
            throw new IllegalArgumentException("You cannot pass inn null");
        }
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Empty username field");
        }
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Empty password field");
        }
        String path = System.getProperty("user.dir")
                .replace("rest", "core")
                + "/src/main/resources/savefiles/users"
                + "/auth/auth.json";
        try (Reader reader = new FileReader(path, Charset.forName("UTF-8"))) {
            Gson gson = new Gson();

            // .getBytes(Charset.forName("UTF-8"))
            Type userListType = new TypeToken<ArrayList<LoginDetails>>() {
            }.getType();
            ArrayList<LoginDetails> userArray =
                    gson.fromJson(reader, userListType);

            for (LoginDetails details : userArray) {
                if (details.getUsername().equals(username)
                        && details.getPassword().equals(password)) {
                    return details;
                }
            }

            throw new IllegalArgumentException("No such user exists"
                    + " or incorrectpassword.");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
