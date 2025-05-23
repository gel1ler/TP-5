package ru.bmstu.repository.impl;

import org.springframework.core.io.Resource;
import ru.bmstu.model.Student;
import ru.bmstu.repository.StudentRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepositoryCsvImpl implements StudentRepository {
    private final Resource csvResource;
    private final List<Student> students = new ArrayList<>();

    public StudentRepositoryCsvImpl(Resource csvResource) throws IOException {
        this.csvResource = csvResource;
        loadStudents();
    }

    private void loadStudents() throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(csvResource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    students.add(new Student(
                            parts[0].trim(),
                            parts[1].trim(),
                            Integer.parseInt(parts[2].trim())
                    ));
                }
            }
        }
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    @Override
    public Optional<Student> findByName(String firstName, String lastName) {
        return students.stream()
                .filter(s -> s.getFirstName().equals(firstName) &&
                        s.getLastName().equals(lastName))
                .findFirst();
    }

    @Override
    public void save(Student student) {
        students.add(student);
    }

    @Override
    public void delete(Student student) {
        students.remove(student);
    }

    @Override
    public void saveAll(List<Student> students) {
        this.students.clear();
        this.students.addAll(students);
    }
}