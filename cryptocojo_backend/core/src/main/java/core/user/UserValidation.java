package core.user;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public final class UserValidation {

    private UserValidation() {
        // not called
    }

    /**
     * Path for auth.json-file.
     */
    public static final Path PATHAUTH = Paths.get(System.getProperty("user.dir")
            .replace("rest", "core").replace("ui", "core")
            + "/src/main/resources/savefiles/users/auth/auth.json");

    /**
     * Dirpath - path to where we save our users.
     */
    public static final String DIRPATH = (System.getProperty("user.dir")
            .replace("rest", "core").replace("ui", "core")
            + "/src/main/resources/savefiles/users/");

    /**
     * Reading the auth.json-file and adding them to the returning array.
     *
     * @return An array of UserAuth
     */
    private static UserAuth[] getAuthUsers() {
        String authString = "";
        try {
            authString = Files.readString(PATHAUTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson parser = new Gson();
        UserAuth[] authUsers = parser.fromJson(authString, UserAuth[].class);
        return authUsers;
    }

    /**
     * Accessing the different user-files and adding them to the returning list.
     *
     * @return List of files
     */
    private static List<File> getJsonFilesInDirectory() {
        List<File> files = new ArrayList<File>();
        try {
            File dir = new File(DIRPATH);
            File[] listFiles = dir.listFiles();
            if (listFiles != null) {
                for (File f : listFiles) {
                    if (f.isFile() && f.getName().endsWith(".json")) {
                        if (!f.getName().endsWith("test.json")) {
                            files.add(f);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new ArrayList<File>(files);
    }

    /**
     * Reading the different user-files and adding the
     * username to the returning list.
     *
     * @param files
     * @return List of usernames
     */
    private static List<String> readingFiles(final List<File> files) {
        List<String> usernames = new ArrayList<String>();
        for (File file : files) {
            try (Reader reader = new FileReader(
                    file, Charset.forName("UTF-8"))) {
                JsonElement obj = JsonParser.parseReader(reader);
                String name = obj.getAsJsonObject()
                        .get("name").toString().toLowerCase();
                usernames.add(name.substring(1, name.length() - 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<String>(usernames);
    }

    /**
     * Adding usernames from user-files and auth-users.
     *
     * @return List of usernames
     */
    private static List<String> getUserNames() {
        List<String> usernames = readingFiles(getJsonFilesInDirectory());
        UserAuth[] authUsers = getAuthUsers();
        if (authUsers != null) {
            for (UserAuth userAuth : authUsers) {
                usernames.add(userAuth.getUserName());
            }
        }
        return new ArrayList<>(usernames);
    }

    /**
     * Returns true if the username is unique for the current users,
     * false if not.
     *
     * @param username
     * @return boolean
     */
    public static boolean checkForUniqueUsername(final String username) {
        List<String> usernames = getUserNames();
        if (usernames.contains(username)) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the email is matching with the regex.
     *
     * @param mail
     * @return boolean
     */
    public static boolean checkIfValidEmail(final String mail) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern).matcher(mail).matches();
    }

    /**
     * Checks if the username has a lenth thats i bigger than 1,
     * and is also checking if the username is unique.
     *
     * @param username
     * @return boolean
     */
    public static boolean checkIfValidUsername(final String username) {
        if (username.length() < 2) {
            throw new IllegalArgumentException("The name must be"
                    + " longer than 1 character");
        }
        if (!checkForUniqueUsername(username)) {
            throw new IllegalArgumentException("The name must be unique");
        }
        return true;
    }
}
