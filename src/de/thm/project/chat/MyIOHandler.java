package de.thm.project.chat;

import java.util.ArrayList;
import java.util.Scanner;

public class MyIOHandler {
    // Class attribute
    private static Scanner scanner = new Scanner(System.in);

    // Method to read a string from the console
    public static String readString() {
        String line = scanner.nextLine();
        return line;
    }

    // Method to read a number from the console
    public static int readInt() {
        int number = Integer.parseInt(scanner.nextLine());
        return number;
    }

    // Method to read multiple numbers separated by a space
    public static ArrayList<Integer> readIntegers() {
        ArrayList<Integer> numbers = new ArrayList<>();

        String line = scanner.nextLine();

        String[] array = line.split(" ");
        for(String number : array) {
            numbers.add(Integer.parseInt(number));
        }

        return numbers;
    }

    // Method to get the file ending of a file
    public static String getFileEnding(String path) {
        String[] splittedPath = path.split("\\.");
        String ending = splittedPath[splittedPath.length - 1];

        return ending;
    }
}
