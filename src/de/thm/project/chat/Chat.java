package de.thm.project.chat;

import de.thm.oop.chat.base.server.BasicTHMChatServer;
import de.thm.project.hamster.AStarAlgorithm;
import de.thm.project.hamster.Field;
import de.thm.project.hamster.Hamster;
import de.thm.project.hamster.Territorium;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Chat {
    // Class attributes
    private User user;
    private Group group;
    private BasicTHMChatServer server;

    // Constructor
    public Chat() {
        // Create a THM server object
        server = new BasicTHMChatServer();
        // Create an empty group
        group = new Group();
        // Set the current "logged in" user to null
        user = null;
    }

    // Debug Constructor for faster testing
    public Chat(User user) {
        // Create a THM server object
        server = new BasicTHMChatServer();
        // Create an empty group
        group = new Group();
        this.user = user;
        // Direct start the chat with the given user. No actual login needed
        startChat();
    }

    // Method to login to the chat
    public void loginToChat() {
        System.out.println("Willkommen beim Chat Client von Team A.");

        // bool attribute to check if the user logged in correctly or not
        boolean loggedIn = false;

        do {
            // Get username from console
            System.out.print("Bitte geben Sie Ihren USERNAMEN ein: ");
            String username = MyIOHandler.readString();

            // Get password from console
            System.out.print("Bitte geben Sie Ihr PASSWORT ein: ");
            String password = MyIOHandler.readString();

            // if no user logged in yet
            if(user == null) {
                // create a new user with the username and password given by the user input
                user = new User(username, password);
            }
            // if an user already logged in, change the username and password to the new user given by the user input
            else {
                user.setUsername(username);
                user.setPassword(password);
            }

            // Try to get the users from the server. A proof method to check whether the username and password is correct or not
            try {
                server.getUsers(user.getUsername(), user.getPassword());
                // if no exception is thrown ==> Username and Password are correct
                loggedIn = true;
            } catch(IOException e) {
                // if an exception is thrown ==> repeat the input section
                System.out.println("Username oder Passwort falsch!");
            }
        }while(!loggedIn);

        // start the actual chat program
        startChat();
    }

    // main chat loop method
    private void startChat() {
        String input = "";
        boolean exit = false;
        do {
            // print the main menu
            printMainMenu();
            // get an user input
            input = MyIOHandler.readString();
            // check the input and start the correct process according to the input
            exit = checkInput(input);
            // leave the loop if the aborting key 'q' is pressed
        } while(!exit);
    }

    // Method to select one or multiple chat partners
    private void selectChatpartner() {
        // Get all available chat users
        String[] users = getAllUsers();
        // continue if the at least one user is found
        if(users != null) {
            // user count variable
            int cnt = 1;
            System.out.println("\n-------- Users --------");
            // print the actual users
            for(String user : users) {
                System.out.println("[" + cnt + "] " + user);
                // count every user
                cnt++;
            }
            // Select one or multiple users. The correct splitting between users is important here
            // Splitting symbol is a space between numbers
            System.out.print("Wählen Sie einen oder mehrere (mit Leertaste getrennt) Chatpartner aus Nr.: ");
            ArrayList<Integer> indices = MyIOHandler.readIntegers();

            // Clear the current list to refill it with new users
            group.clearGroup();
            for(int index : indices) {
                // check whether the given index is not zero and is not bigger than the user count
                if(index > 0 && index < cnt) {
                    // add the user to the group
                    group.addUser(new User(users[index - 1]));
                }
                else {
                    System.out.println("User mit der Nr. " + index + " ist nicht vorhanden!");
                }
            }
        }
    }

    // Check the given input and start the corresponding process
    private boolean checkInput(String input) {
        boolean exit = false;
        switch(input) {
            case "q":
            case "Q":
                // exit the program
                exit = true;
                break;
            case "1":
                // select one or multiple chat partners
                selectChatpartner();
                break;
            case "2":
                // send a text or an image message
                messageMenu();
                break;
            case "3":
                // get all latesst 100 messages
                printLatestMsgs(getLatestMsgs());
                break;
            case "4":
                // login with a new username and password
                loginToChat();
                break;
            case "5":
                // start hamster automation chat
                startHamsterChat();
                break;
            default:
                // no correct input symbol
                System.out.println("Diese Eingabe ist nicht gültig");
                break;
        }

        return exit;
    }

    // Method to start the automated chat with the hamster simulator
    private void startHamsterChat() {
        // Create the hamster chat partner object
        User hamsterPartner = new User("hamster19ws");

        Hamster hamster = new Hamster(server, user, hamsterPartner);
        hamster.init();

        sleep(5000);

        Territorium territorium = new Territorium(getLatestMsgs());

        AStarAlgorithm algorithm = new AStarAlgorithm(territorium, hamster);
        algorithm.start();
    }

    private void sleep(long sleepTime) {
        try {
            // Sleep to wait for the server answer
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to select between sending a text or an image message
    private void messageMenu() {
        // Check whether at least one user is selected to chat with or not
        if(!group.hasUsers()) {
            System.out.println("\nEs wurde(n) kein(e) Chatpartner ausgewählt!");
        }
        // If at least one user is selected. Choose between text or image message
        else {
            System.out.println("\nWelcher Nachrichtentyp soll verschickt werden: ");
            System.out.println("[1] Textnachricht");
            System.out.println("[2] Bildnachricht");
            System.out.print("Ihre Auswahl: ");

            int input = MyIOHandler.readInt();

            switch(input) {
                // '1' equals text message
                case 1:
                    System.out.println("Ihre Nachricht: ");
                    // Get message from the user by the console
                    String msg = MyIOHandler.readString();

                    // Create the text message object without a receiver
                    TextMessage textMsg = new TextMessage(server, null, msg);

                    for(User member : group.getUsers()) {
                        // Set the receiver for each member in the group
                        textMsg.setReceiver(member);
                        // Send the message to each member
                        textMsg.send(user);
                    }
                    break;
                // '2' equals image message
                case 2:
                    System.out.print("Dateipfad: ");
                    // Get the path to the image from the user
                    String path = MyIOHandler.readString();
                    // get the file ending of the given image (png or jpg)
                    String fileEnding = MyIOHandler.getFileEnding(path);
                    // correct the jpg mime type to the mime type which the server accepts
                    // jpg or jpe needs to be changed to jpeg
                    // png will be accepted normally
                    if(fileEnding.equals("jpg") || fileEnding.equals("jpe")) {
                        fileEnding = "jpeg";
                    }
                    // concat the mime type with the image tag and the file ending
                    String mimeType = "image/" + fileEnding;

                    InputStream imageData = null;

                    // try to create a file input stream from the given image path
                    try {
                        imageData = new FileInputStream(path);

                        // If the input stream creates normally, send the message to the group

                        // Create the image message object without a receiver
                        ImgMessage imgMsg = new ImgMessage(server, null, mimeType, imageData);
                        for(User member : group.getUsers()) {
                            // Set the receiver for each member in the group
                            imgMsg.setReceiver(member);
                            // Send the image to each member
                            imgMsg.send(user);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("Das Bild konnte nicht gesendet werden, da die Datei nicht geöffnet werden konnte");
                    }

                    break;
                default:
                    System.out.println("Diese Eingabe ist nicht gültig");
                    break;
            }
        }
    }

    // Method to print the last 100 sent or received messages
    private String[] getLatestMsgs() {
        try {
            // Try to get latest messages from the server
            String[] msgs = server.getMostRecentMessages(user.getUsername(), user.getPassword());
            return msgs;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void printLatestMsgs(String[] msgs) {
        // Print messages
        if(msgs != null) {
            for(String msg : msgs) {
                System.out.println(msg);
            }
            printPressEnter();
        }
        else {
            System.out.println("Noch keine Nachrichten :(");
        }
    }

    private String[] getAllUsers() {
        String[] users = null;
        try {
            // Try to get all users from the server
            users = server.getUsers(user.getUsername(), user.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Print methods
    private void printPressEnter() {
        System.out.println("Drücken Sie ENTER um fortzusetzen!");
        MyIOHandler.readString();
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
        System.out.println("[5] Automatischen Hamster starten");
        System.out.println("[Q] Chat Programm beenden");
        System.out.print("Ihre Auswahl: ");
    }
}
