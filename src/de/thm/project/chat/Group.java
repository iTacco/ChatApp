package de.thm.project.chat;

import java.util.ArrayList;

public class Group {
    // List with Chat partners
    ArrayList<User> users;

    // Constructor which creates an empty arraylist
    public Group() {
        users = new ArrayList<>();
    }

    // Method to add an user to the group
    public void addUser(User user) {
        users.add(user);
    }

    // Method to get all users from the group as an arraylist
    public ArrayList<User> getUsers() {
        return users;
    }

    // Method to check whether the group has at least one user or not
    public boolean hasUsers() {
        return !users.isEmpty();
    }

    // Method to clear the entire group
    public void clearGroup() {
        users.clear();
    }

    // Method to print all users or print 'no users selected'
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
