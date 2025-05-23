package ru.bmstu.service;

import ru.bmstu.model.Student;
import ru.bmstu.model.UserRole;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    void addStudent(String firstName, String lastName, int tokens, UserRole currentRole);
    void updateTokens(String firstName, String lastName, int change, UserRole currentRole);
    void expelStudent(String firstName, String lastName, UserRole currentRole);
}