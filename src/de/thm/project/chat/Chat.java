package de.thm.project.chat;

import de.thm.oop.chat.base.server.BasicTHMChatServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Chat {
    private User user;
    private Group group;
    private BasicTHMChatServer server;
    private MyIOHandler ioHandler;

    public Chat() {
        // Create a THM server object
        server = new BasicTHMChatServer();
        group = new Group();
        // Creating a IOHandler to handle all console inputs and outputs
        ioHandler = new MyIOHandler();
        user = null;
    }

    // Debug Constructor for faster testing
    public Chat(User user) {
        // Create a THM server object
        server = new BasicTHMChatServer();
        group = new Group();
        // Creating a IOHandler to handle all console inputs and outputs
        ioHandler = new MyIOHandler();
        this.user = user;
        startChat();
    }

    public void loginToChat() {
        System.out.println("Willkommen beim Chat Client von Team A.");

        boolean loggedIn = false;

        do {
            // Get Username
            System.out.print("Bitte geben Sie Ihren USERNAMEN ein: ");
            String username = ioHandler.readString();

            // Get password
            System.out.print("Bitte geben Sie Ihr PASSWORT ein: ");
            String password = ioHandler.readString();

            if(user == null) {
                user = new User(username, password);
            }
            else {
                user.setUsername(username);
                user.setPassword(password);
            }

            try {
                server.getUsers(user.getUsername(), user.getPassword());
                loggedIn = true;
            } catch(IOException e) {
                System.out.println("Username oder Passwort falsch!");
            }
        }while(!loggedIn);

        startChat();
    }

    private void startChat() {
        String input = "";
        boolean exit = false;
        do {
            printMainMenu();
            input = ioHandler.readString();
            exit = checkInput(input);
        } while(!exit);
    }

    private void printPressEnter() {
        System.out.println("Drücken Sie ENTER um fortzusetzen!");
        ioHandler.readString();
    }

    private void printMainMenu() {
        System.out.println("------------------");
        System.out.println("Eingeloggt: " + user.getUsername());
        System.out.print("Aktuelle(r) Chatpartner: ");
        group.printUsers();
        System.out.println("[1] Chatpartner auswähen");
        System.out.println("[2] Nachricht senden");
        System.out.println("[3] Nachrichten lesen");
        System.out.println("[4] Benutzer wechseln");
        System.out.println("[Q] Chat Programm beenden");
        System.out.print("Ihre Auswahl: ");
    }



    private void selectChatpartner() {
        // Get all chat users
        String[] users = getAllUsers();
        if(users != null) {
            int cnt = 1;
            System.out.println("\n-------- Users --------");
            for(String user : users) {
                System.out.println("[" + cnt + "] " + user);
                cnt++;
            }
            System.out.print("Wählen Sie einen oder mehrere (mit Leertaste getrennt) Chatpartner aus Nr.: ");
            ArrayList<Integer> indices = ioHandler.readIntegers();

            // Clear the current list to refill it with new users
            group.clearGroup();
            for(int index : indices) {
                if(index >= 0 && index < cnt) {
                    group.addUser(new User(users[index - 1]));
                }
                else {
                    System.out.println("User mit der Nr. " + index + " ist nicht vorhanden!");
                }
            }
        }
    }

    private boolean checkInput(String input) {
        boolean exit = false;
        switch(input) {
            case "q":
            case "Q":
                exit = true;
                break;
            case "1":
                selectChatpartner();
                break;
            case "2":
                messageMenu();
                break;
            case "3":
                getLatestMsgs();
                break;
            case "4":
                loginToChat();
                break;
            default:
                System.out.println("Diese Eingabe ist nicht gültig");
                break;
        }

        return exit;
    }

    private void messageMenu() {
        if(!group.hasUsers()) {
            System.out.println("\nEs wurde(n) kein(e) Chatpartner ausgewählt!");
        }
        else {
            System.out.println("\nWelcher Nachrichtentyp soll verschickt werden: ");
            System.out.println("[1] Textnachricht");
            System.out.println("[2] Bildnachricht");
            System.out.print("Ihre Auswahl: ");

            int input = ioHandler.readInt();

            switch(input) {
                case 1:
                    System.out.println("Ihre Nachricht: ");
                    String msg = ioHandler.readString();
                    for(User member : group.getUsers()) {
                        TextMessage textMsg = new TextMessage(server, member, msg);
                        textMsg.send(user);
                    }
                    break;
                case 2:
                    System.out.print("Dateipfad: ");
                    String path = ioHandler.readString();
                    String fileEnding = ioHandler.getFileEnding(path);
                    if(fileEnding.equals("jpg") || fileEnding.equals("jpe")) {
                        fileEnding = "jpeg";
                    }
                    String mimeType = "image/" + fileEnding;

                    InputStream imageData = null;

                    try {
                        imageData = new FileInputStream(path);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    for(User member : group.getUsers()) {
                        ImgMessage imgMsg = new ImgMessage(server, member, mimeType, imageData);
                        imgMsg.send(user);
                    }

                    break;
                default:
                    System.out.println("Diese Eingabe ist nicht gültig");
                    break;
            }
        }
    }

    private void getLatestMsgs() {
        try {
            String[] msgs = server.getMostRecentMessages(user.getUsername(), user.getPassword());
            for(String msg : msgs) {
                System.out.println(msg);
            }
            printPressEnter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] getAllUsers() {
        String[] users = null;
        try {
            users = server.getUsers(user.getUsername(), user.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
}
