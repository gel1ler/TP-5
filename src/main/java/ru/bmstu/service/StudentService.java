package ru.bmstu.service;

import ru.bmstu.model.Student;
import ru.bmstu.model.UserRole;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudents();
    Optional<Student> getById(long id);
    void addStudent(String firstName, String lastName, int tokens);
    void addStudent(Student student);
    void updateTokens(long id, int change);
    void updateTokens(Student student, int change);
    void expelStudent(Student student);
    void expelStudent(long id);
}