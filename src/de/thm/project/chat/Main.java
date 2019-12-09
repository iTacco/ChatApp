package de.thm.project.chat;

public class Main {

    public static void main(String[] args) {
        String username = "iTacco";
        String password = "u0dA6Mr2";

        //String username = "LifeCoder";
        //String password = "Bs9O4noy";


        // Create a user with the given username and password
        User user = new User(username, password);
        Chat chat = new Chat(user);
        //chat.loginToChat();
    }
}
