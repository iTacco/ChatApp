package de.thm.project.chat;

import de.thm.oop.chat.base.server.BasicTHMChatServer;

import java.io.IOException;

public class TextMessage extends Message {
    private String text;

    public TextMessage(BasicTHMChatServer server, User receiver, String text) {
        super(server, receiver);
        this.text = text;
    }

    public void send(User user) {
        try {
            server.sendTextMessage(user.getUsername(), user.getPassword(), receiver.getUsername(), text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return text;
    }
}
