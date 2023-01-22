package core.login;

public class LoginDetails {

    /**
     * username - username of the user trying to login.
     */
    private String username;

    /**
     * password - password of the user trying to login.
     */
    private String password;

    /**
     * uUID - id of the user trying to login.
     */
    private String userId;

    /**
     * Constructor for LoginDetails class.
     *
     * @param usernameIn
     * @param passwordIn
     * @param userIdIn
     */
    public LoginDetails(final String usernameIn,
            final String passwordIn, final String userIdIn) {
        this.password = passwordIn;
        this.userId = userIdIn;
        this.username = usernameIn;
    }

    /**
     * @return String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @return String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @return String
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * @return LoginDetails in readable format.
     */
    @Override
    public String toString() {
        return "LoginDetails [username=" + username + ", password="
                + password + ", userId=" + userId + "]";
    }

}
