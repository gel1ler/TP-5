package ru.bmstu;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.bmstu.aspect.RoleCheckAspect;
import ru.bmstu.config.AppConfig;
import ru.bmstu.model.UserRole;
import ru.bmstu.service.StudentService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        StudentService studentService = context.getBean(StudentService.class);
        RoleCheckAspect roleCheckAspect = context.getBean(RoleCheckAspect.class);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Student Tokens Management System");
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter your role (student/teacher): ");
        UserRole role = UserRole.valueOf(scanner.nextLine().toUpperCase());

        roleCheckAspect.setCurrentUserRole(role);

        boolean running = true;
        while (running) {
            System.out.println("\nOptions:");
            System.out.println("1. List all students");
            System.out.println("2. Add new student");
            System.out.println("3. Update tokens");
            System.out.println("4. Expel student");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1:
                        studentService.getAllStudents().forEach(s ->
                                System.out.printf("%s %s: %d tokens%n",
                                        s.getFirstName(), s.getLastName(), s.getTokens()));
                        break;
                    case 2:
                        if (role == UserRole.TEACHER) {
                            System.out.print("Enter student first name: ");
                            String newFirstName = scanner.nextLine();
                            System.out.print("Enter student last name: ");
                            String newLastName = scanner.nextLine();
                            System.out.print("Enter initial tokens: ");
                            int tokens = scanner.nextInt();
                            studentService.addStudent(newFirstName, newLastName, tokens, role);
                        } else {
                            System.out.println("Permission denied. Only teachers can add students.");
                        }
                        break;
                    case 3:
                        if (role == UserRole.TEACHER) {
                            System.out.print("Enter student first name: ");
                            String updateFirstName = scanner.nextLine();
                            System.out.print("Enter student last name: ");
                            String updateLastName = scanner.nextLine();
                            System.out.print("Enter token change (+/-): ");
                            int change = scanner.nextInt();
                            studentService.updateTokens(updateFirstName, updateLastName, change, role);
                        } else {
                            System.out.println("Permission denied. Only teachers can update tokens.");
                        }
                        break;
                    case 4:
                        if (role == UserRole.TEACHER) {
                            System.out.print("Enter student first name to expel: ");
                            String expelFirstName = scanner.nextLine();
                            System.out.print("Enter student last name to expel: ");
                            String expelLastName = scanner.nextLine();
                            studentService.expelStudent(expelFirstName, expelLastName, role);
                        } else {
                            System.out.println("Permission denied. Only teachers can expel students.");
                        }
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        context.close();
        System.out.println("Goodbye!");
    }
}