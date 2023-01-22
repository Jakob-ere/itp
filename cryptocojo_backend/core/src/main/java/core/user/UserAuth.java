package core.user;

/**
 * @author Oskar Nesheim
 */
public class UserAuth {

    /**
     * username - username of the user object.
     */
    private String username;

    /**
     * password - password of the user object.
     */
    private String password;

    /**
     * UUID - UUID of the user object.
     */
    private String userId;

    /**
     * Constructor for creating a UserAuth.
     *
     * @param name         - name of the user who wants to login
     * @param authPassword - password for the user who wants to login
     * @param uniUID       - id for the user who wants to login
     */
    public UserAuth(final String name,
            final String authPassword, final String uniUID) {
        this.username = getUserName();
        this.password = getPassword();
        this.userId = getUserId();
    }

    /**
     * Empty constructor.
     */
    public UserAuth() {
    }

    /**
     * @param name
     */
    public void setUsername(final String name) {
        this.username = name;
    }

    /**
     * @param authPassword
     */
    public void setPassword(final String authPassword) {
        this.password = authPassword;
    }

    /**
     * @param uniUID
     */
    public void setUserId(final String uniUID) {
        this.userId = uniUID;
    }

    /**
     * @return String
     */
    protected String getUserName() {
        return this.username;
    }

    /**
     * @return String
     */
    private String getPassword() {
        return this.password;
    }

    /**
     * @return String
     */
    public String getUserId() {
        return this.userId;
    }
}
