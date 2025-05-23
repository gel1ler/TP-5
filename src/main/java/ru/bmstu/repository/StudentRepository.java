package ru.bmstu.repository;

import ru.bmstu.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<Student> findAll();
    Optional<Student> findByName(String firstName, String lastName);
    void save(Student student);
    void delete(Student student);
    void saveAll(List<Student> students);
}
