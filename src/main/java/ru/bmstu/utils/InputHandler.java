package ru.bmstu.utils;

import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getIntInput() {
        return scanner.nextInt();
    }

    public static String getStringInput() {
        return scanner.nextLine();
    }
}
