package ru.bmstu.repository.impl;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.bmstu.model.Student;
import ru.bmstu.repository.StudentRepository;
import ru.bmstu.utils.Menu;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepositoryCsvImpl implements StudentRepository {
    private final Resource csvResource;
    private final List<Student> students = new ArrayList<>();
    private Long lastId;

    public StudentRepositoryCsvImpl(Resource csvResource) throws IOException {
        this.csvResource = csvResource;
        loadStudents();
    }

    public long getNewId() {
        return ++lastId;
    }

    private void loadStudents() throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(csvResource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    lastId = Long.parseLong(parts[0].trim());
                    students.add(new Student(
                            lastId,
                            parts[1].trim(),
                            parts[2].trim(),
                            Integer.parseInt(parts[3].trim())
                    ));
                }
            }
            if (lastId == null) lastId = 0L;
        }
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    @Override
    public Optional<Student> findById(long id) {
        return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst();
    }


    @Override
    public void add(Student student) {
        students.add(student);
        saveStudents();
    }

    @Override
    public void delete(Student student) {
        students.remove(student);
        saveStudents();
    }

    private void saveStudents() {
        try {
            File tempFile = File.createTempFile("students", ".tmp");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                for (Student student : students) {
                    writer.write(String.format("%d,%s,%s,%d\n",
                            student.getId(),
                            student.getFirstName(),
                            student.getLastName(),
                            student.getTokens()));
                }
            }

            Path targetPath = Paths.get(csvResource.getURI());
            Files.copy(tempFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            tempFile.delete();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save students to CSV", e);
        }
    }
}