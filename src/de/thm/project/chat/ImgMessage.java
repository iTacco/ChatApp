package de.thm.project.chat;

import de.thm.oop.chat.base.server.BasicTHMChatServer;

import java.io.IOException;
import java.io.InputStream;

public class ImgMessage extends Message {
    private String mimeType;
    private InputStream imageData;

    public ImgMessage(BasicTHMChatServer server, User receiver, String mimeType, InputStream imageData) {
        super(server, receiver);
        this.mimeType = mimeType;
        this.imageData = imageData;
    }

    public void send(User user) {
        try {
            server.sendImageMessage(user.getUsername(), user.getPassword(), receiver.getUsername(), mimeType, imageData);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getMimeType() {
        return mimeType;
    }

    public InputStream getImageData() {
        return imageData;
    }
}
