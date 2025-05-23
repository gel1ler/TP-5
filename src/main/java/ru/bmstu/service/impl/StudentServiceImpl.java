package ru.bmstu.service.impl;

import org.springframework.stereotype.Service;
import ru.bmstu.model.Student;
import ru.bmstu.model.UserRole;
import ru.bmstu.repository.StudentRepository;
import ru.bmstu.service.JournalService;
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
    public void addStudent(String firstName, String lastName, int tokens, UserRole currentRole) {
        Student newStudent = new Student(firstName, lastName, tokens);
        studentRepository.save(newStudent);
        journalService.logAction(currentRole,
                "Added student: " + firstName + " " + lastName + " with " + tokens + " tokens");
    }

    @Override
    public void updateTokens(String firstName, String lastName, int change, UserRole currentRole) {
        Optional<Student> studentOpt = studentRepository.findByName(firstName, lastName);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setTokens(student.getTokens() + change);
            journalService.logAction(currentRole,
                    "Updated tokens for " + firstName + " " + lastName + " by " + change);
        }
    }

    @Override
    public void expelStudent(String firstName, String lastName, UserRole currentRole) {
        Optional<Student> studentOpt = studentRepository.findByName(firstName, lastName);
        if (studentOpt.isPresent()) {
            studentRepository.delete(studentOpt.get());
            journalService.logAction(currentRole,
                    "Expelled student: " + firstName + " " + lastName);
        }
    }
}