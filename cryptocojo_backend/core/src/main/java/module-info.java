module core {
    requires transitive com.google.gson;

    opens core.user to com.google.gson;
    opens core.items to com.google.gson;
    opens core.rest to com.google.gson;
    opens core.login to com.google.gson;

    exports core.items;
    exports core.login;
    exports core.rest;
    exports core.user;

}
