package de.thm.project.chat;

import de.thm.oop.chat.base.server.BasicTHMChatServer;

import java.io.IOException;

public class TextMessage extends Message {
    // Class attributes
    private String text;

    // Constructor
    public TextMessage(BasicTHMChatServer server, User receiver, String text) {
        // Super constructor from Class Message
        super(server, receiver);
        this.text = text;
    }

    // Send method
    public void send(User user) {
        try {
            // Try to send the message with the given logged in user and the current chat partner and message
            server.sendTextMessage(user.getUsername(), user.getPassword(), receiver.getUsername(), text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter
    public String getText() {
        return text;
    }
}
