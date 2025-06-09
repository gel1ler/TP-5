package ru.bmstu.utils;

import ru.bmstu.model.Student;

import java.util.List;

public class Menu {
    public static void println(int i) {
        System.out.println(i);
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void print(int i) {
        System.out.print(i);
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static void listStudents(List<Student> list) {
        list.forEach(s ->
                System.out.printf("%s %s: %d tokens%n",
                        s.getFirstName(), s.getLastName(), s.getTokens()));
    }

    public static void listStudents(List<Student> list, boolean withNumeric) {
        if (withNumeric) {
            for (int i = 0; i < list.size(); i++) {
                Student s = list.get(i);
                System.out.printf("%d. %s %s: %d tokens%n",
                        i + 1,
                        s.getFirstName(),
                        s.getLastName(),
                        s.getTokens());
            }
        } else {
            listStudents(list);
        }
    }
}
