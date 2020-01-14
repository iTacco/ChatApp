package de.thm.project.chat;

public class Main {

    public static void main(String[] args) {

        /*User user = new User("iTacco", "u0dA6Mr2");
        Chat chat = new Chat(user);*/



        // Create a new chat object
        Chat chat = new Chat();
        // Login to chat. Username and password will be asked after this method call
        chat.loginToChat();
    }
}
