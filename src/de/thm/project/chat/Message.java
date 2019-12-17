package de.thm.project.chat;

import de.thm.oop.chat.base.server.BasicTHMChatServer;

public abstract class Message {
    // Class attributes
    protected BasicTHMChatServer server;
    protected User receiver;

    // Constructor
    public Message(BasicTHMChatServer server, User receiver) {
        this.server = server;
        this.receiver = receiver;
    }

    // Getter, Setter
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getReceiver() {
        return receiver;
    }
}
