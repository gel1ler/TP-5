package ru.bmstu;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.bmstu.aspect.RoleCheckAspect;
import ru.bmstu.config.AppConfig;
import ru.bmstu.model.Student;
import ru.bmstu.model.UserRole;
import ru.bmstu.service.StudentService;
import ru.bmstu.service.RoleSelector;

import java.util.List;

import static ru.bmstu.utils.InputHandler.getIntInput;
import static ru.bmstu.utils.InputHandler.getStringInput;
import static ru.bmstu.utils.Menu.*;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        StudentService studentService = context.getBean(StudentService.class);
        RoleCheckAspect roleCheckAspect = context.getBean(RoleCheckAspect.class);

        UserRole role = RoleSelector.selectRole();

        roleCheckAspect.setCurrentUserRole(role);

        boolean isRunning = true;
        while (isRunning) {
            List<Student> students = studentService.getAllStudents();

            println("\nOptions:");
            println("1. List all students");
            println("2. Add new student");
            println("3. Update tokens");
            println("4. Expel student");
            println("5. Exit");
            print("Choose an option: ");

            int choice = getIntInput();
            getStringInput();

            try {
                switch (choice) {
                    case 1:
                        listStudents(students);
                        break;
                    case 2:
                        if (role == UserRole.TEACHER) {
                            print("Enter student first name: ");
                            String newFirstName = getStringInput();
                            print("Enter student last name: ");
                            String newLastName = getStringInput();
                            print("Enter initial tokens: ");
                            int tokens = getIntInput();
                            studentService.addStudent(newFirstName, newLastName, tokens);
                        } else {
                            println("Permission denied. Only teachers can add students.");
                        }
                        break;
                    case 3:
                        if (role == UserRole.TEACHER) {
                            listStudents(students, true);
                            print("Choose student to update: ");
                            int i = getIntInput() - 1;
                            print("Enter token increment: ");
                            int change = getIntInput();
                            studentService.updateTokens(students.get(i).getId(), change);
                        } else {
                            println("Permission denied. Only teachers can update tokens.");
                        }
                        break;
                    case 4:
                        if (role == UserRole.TEACHER) {
                            listStudents(students, true);
                            print("Choose student to expel: ");
                            int i = getIntInput() - 1;
                            studentService.expelStudent(students.get(i));
                        } else {
                            println("Permission denied. Only teachers can expel students.");
                        }
                        break;
                    case 5:
                        isRunning = false;
                        break;
                    default:
                        println("Invalid option");
                }
            } catch (Exception e) {
                println("Error: " + e.getMessage());
            }
        }

        context.close();
        println("Goodbye!");
    }
}