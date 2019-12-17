package de.thm.project.chat;

import de.thm.oop.chat.base.server.BasicTHMChatServer;

import java.io.IOException;
import java.io.InputStream;

public class ImgMessage extends Message {
    // Class attributes
    private String mimeType;
    private InputStream imageData;

    // Constructor
    public ImgMessage(BasicTHMChatServer server, User receiver, String mimeType, InputStream imageData) {
        // Super constructor from Class Message
        super(server, receiver);
        this.mimeType = mimeType;
        this.imageData = imageData;
    }

    // Send method
    public void send(User user) {
        try {
            // Try to send the message with the given logged in user and the current chat partner and mime type and image data
            server.sendImageMessage(user.getUsername(), user.getPassword(), receiver.getUsername(), mimeType, imageData);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Getter
    public String getMimeType() {
        return mimeType;
    }
    public InputStream getImageData() {
        return imageData;
    }
}
