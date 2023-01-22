module rest {
    requires transitive core;

    requires spring.web;
    requires spring.beans;
    requires spring.boot;
    requires spring.context;
    requires spring.boot.autoconfigure;

    requires transitive com.google.gson;

    // opens rest to spring.beans, spring.context, spring.web;
    opens rest;
}
