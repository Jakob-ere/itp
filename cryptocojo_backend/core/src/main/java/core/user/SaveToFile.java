package core.user;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import java.nio.charset.Charset;

/**
 * @author Oskar Nesheim
 * @author Ole Dahl
 * @author Jakob Relling
 */
public final class SaveToFile {

    private SaveToFile() {
        // not called
    }

    /**
     * Method for serializing a User object into a .json file.
     *
     * @author Ole Dahl
     * @param user Object to serialize
     * @throws IOException
     */
    public static void saveUserToFile(final User user)
            throws IllegalArgumentException, IOException {
        if (user == null) {
            throw new IllegalArgumentException("The user must exist");
        }
        File dir = new File(System.getProperty("user.dir")
                .replace("rest", "core")
                + "/src/main/resources/savefiles/users/"
                + user.getUserId().toString() + ".json");

        Gson gson = new Gson();
        Writer writer = new FileWriter(dir, Charset.forName("UTF-8"));
        try {
            writer.write(gson.toJson(user, User.class));
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            writer.close();
        }

    }

    /**
     * Saves the user authentication information for a user into auth.json.
     *
     * @author Oskar Nesheim
     * @param user - the user whos information will be saved
     * @throws IOException
     */
    public static void saveAuthInfo(final User user)
            throws IllegalArgumentException, IOException {
        if (user == null) {
            throw new IllegalArgumentException("The user must exist");
        }
        // We are aware that using a constructor here would be better,
        // but we simply could not get this to work with a constructor.
        List<UserAuth> users = new ArrayList<>();
        UserAuth newUser = new UserAuth();
        newUser.setUsername(user.getName());
        newUser.setUserId(user.getUserId().toString());
        newUser.setPassword(user.getPassword());
        users.add(newUser);

        Gson parser = new Gson();
        try {
            Reader reader = new FileReader(
                    System.getProperty("user.dir").replace("rest", "core")
                            + "/src/main/resources/savefiles/users/auth/auth.json",
                    Charset.forName("UTF-8"));
            UserAuth[] oldUsers = parser.fromJson(reader, UserAuth[].class);
            if (oldUsers != null && oldUsers.length > 0) {
                for (int i = 0; i < oldUsers.length; i++) {
                    users.add(oldUsers[i]);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserAuth[] newArray = new UserAuth[users.size()];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = users.get(i);
        }
        File dir = new File(
                System.getProperty("user.dir").replace("rest", "core")
                        + "/src/main/resources/savefiles/users/auth/auth.json");
        Writer writer = new FileWriter(dir, Charset.forName("UTF-8"));
        try {
            writer.write(parser.toJson(newArray, UserAuth[].class));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}
