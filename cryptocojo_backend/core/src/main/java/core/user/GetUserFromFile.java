package core.user;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

import com.google.gson.Gson;

/*
 * A class that retrieves user object from the database
 */
public final class GetUserFromFile {

    private GetUserFromFile() {
        // not called
    }

    /**
     * Gets a user from the database based on Id.
     *
     * @param userId - userId of the user we want to retrieve
     * @return User - a userobject from saved in the database
     */
    public static User retrieveUser(final String userId) {
        if (userExists(userId)) {
            Gson parser = new Gson();
            try (Reader reader = new FileReader(
                    System.getProperty("user.dir").replace("rest", "core")
                            + "/src/main/resources/savefiles/users/"
                            + userId
                            + ".json",
                    Charset.forName("UTF-8"))) {
                // Convert JSON File to Java Object
                User retrievedUser = parser.fromJson(reader, User.class);
                return retrievedUser;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Checks if a user with the unique UUID has a savefile.
     *
     * @param userId - ID of the user we want to check if exists
     * @return boolean - if the user exists in the database or not
     */
    public static boolean userExists(final String userId) {
        if (userId == null) {
            return false;
        }
        try {
            File dir = new File(
                    System.getProperty("user.dir").replace("rest", "core")
                            + "/src/main/resources/savefiles/users/");
            FindFile filter = new FindFile(userId);
            String[] listStrings = dir.list(filter);
            if (listStrings != null && listStrings.length == 1) {
                return true;
            } else {
                System.out.println("could not find user");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
