package de.thm.project.hamster;

import de.thm.oop.chat.base.server.BasicTHMChatServer;
import de.thm.project.chat.TextMessage;
import de.thm.project.chat.User;

public class Hamster {

    public static final int NORD = 0;
    public static final int OST = 1;
    public static final int SUED = 2;
    public static final int WEST = 3;

    private int row, col, direction;

    private BasicTHMChatServer server;
    private User user;
    private User receiver;

    public Hamster(BasicTHMChatServer server, User user, User receiver) {
        this.row = 0;
        this.col = 0;
        this.direction = OST;
        this.server = server;
        this.user = user;
        this.receiver = receiver;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getDirection() {
        return direction;
    }

    public void init() {
        sendMsg("init");
    }

    public void turnLeft() {
        sendMsg("l");
    }

    public void goForward() {
        sendMsg("v");
    }

    private void sendMsg(String msg) {
        TextMessage txtMsg = new TextMessage(server, receiver, msg);
        txtMsg.send(user);
    }
}
