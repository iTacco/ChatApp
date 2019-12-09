package de.thm.project.chat;

import de.thm.oop.chat.base.server.BasicTHMChatServer;

public abstract class Message {
    protected BasicTHMChatServer server;
    protected User receiver;

    public Message(BasicTHMChatServer server, User receiver) {
        this.server = server;
        this.receiver = receiver;
    }

    public User getReceiver() {
        return receiver;
    }
}
