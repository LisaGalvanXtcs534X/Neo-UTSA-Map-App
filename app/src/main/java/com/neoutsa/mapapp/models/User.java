package com.neoutsa.mapapp.models;

/** Base User class from the UML class diagram. */
public class User {
    protected String name;
    protected String emailAddress;
    protected String password;
    protected boolean loggedIn;

    public User(String name, String emailAddress, String password) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.password = password;
        this.loggedIn = false;
    }

    public boolean login(String email, String pwd) {
        if (this.emailAddress.equalsIgnoreCase(email) && this.password.equals(pwd)) {
            this.loggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() { this.loggedIn = false; }

    public String getName() { return name; }
    public String getEmailAddress() { return emailAddress; }
    public String getPassword() { return password; }
    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean v) { this.loggedIn = v; }
}
