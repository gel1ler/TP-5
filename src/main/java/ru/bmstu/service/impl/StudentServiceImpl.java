package ru.bmstu.service.impl;

import org.springframework.stereotype.Service;
import ru.bmstu.model.Student;
import ru.bmstu.model.UserRole;
import ru.bmstu.repository.StudentRepository;
import ru.bmstu.repository.JournalService;
import ru.bmstu.service.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final JournalService journalService;

    public StudentServiceImpl(StudentRepository studentRepository, JournalService journalService) {
        this.studentRepository = studentRepository;
        this.journalService = journalService;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getById(long id){
        return studentRepository.findById(id);
    };

    @Override
    public void addStudent(String firstName, String lastName, int tokens) {
        long newId = studentRepository.getNewId();
        Student newStudent = new Student(newId, firstName, lastName, tokens);
        studentRepository.add(newStudent);
        journalService.logAction("Added student: " + firstName + " " + lastName + " with " + tokens + " tokens");
    }

    @Override
    public void addStudent(Student student) {
        long newId = studentRepository.getNewId();
        student.setId(newId);

        studentRepository.add(student);
        journalService.logAction("Added student: " + student.getFirstName() + " " + student.getLastName() + " with " + student.getTokens() + " tokens");
    }

    @Override
    public void updateTokens(Student student, int change) {
        if (student != null) {
            student.setTokens(student.getTokens() + change);
            journalService.logAction(
                    String.format("Updated tokens for %s %s by %d",
                            student.getFirstName(),
                            student.getLastName(),
                            change));
        }
    }

    @Override
    public void updateTokens(long id, int change){
        Optional<Student> student = getById(id);
        student.ifPresent(value -> updateTokens(value, change));
    };

    @Override
    public void expelStudent(Student student) {
        if (student != null) {
            studentRepository.delete(student);
            journalService.logAction(
                    String.format("Expelled student: %s %s",
                            student.getFirstName(),
                            student.getLastName()));
        }
    }

    @Override
    public void expelStudent(long id) {
        Optional<Student> student = getById(id);
        if (student.isEmpty()) {
            throw new RuntimeException("Студент с ID " + id + " не найден");
        }
        expelStudent(student.get());
    }
}