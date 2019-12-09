package de.thm.project.chat;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;

public class MyIOHandler {
    private Scanner scanner;

    public MyIOHandler() {
        scanner = new Scanner(System.in);
    }

    public String readString() {
        String line = scanner.nextLine();
        return line;
    }

    public int readInt() {
        int number = Integer.parseInt(scanner.nextLine());
        return number;
    }

    public ArrayList<Integer> readIntegers() {
        ArrayList<Integer> numbers = new ArrayList<>();

        String line = scanner.nextLine();

        String[] array = line.split(" ");
        for(String number : array) {
            numbers.add(Integer.parseInt(number));
        }

        return numbers;
    }

    public String getFileEnding(String path) {
        String[] splittedPath = path.split("\\.");
        String ending = splittedPath[splittedPath.length - 1];

        return ending;
    }

    public String getText() {
        return null;
    }
}
