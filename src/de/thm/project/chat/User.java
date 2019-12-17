package de.thm.project.chat;

public class User {
    // Class attributes
    private String username;
    private String password;

    // Constructor with only username as parameter. Needed for group building
    public User(String username) {
        this.username = username;
        this.password = null;
    }

    // Constructor with username and password. Needed for User login
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter, Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
