package de.thm.project.chat;

import java.util.ArrayList;

public class Group {
    ArrayList<User> users;

    public Group() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean hasUsers() {
        return !users.isEmpty();
    }

    public void clearGroup() {
        users.clear();
    }

    public void printUsers() {
        if(!users.isEmpty()) {
            System.out.print("| ");
            for(User user : users) {
                System.out.print(user.getUsername() + " | ");
            }
            System.out.println("");
        }
        else {
            System.out.println("Keine");
        }
    }
}
