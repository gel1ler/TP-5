package ru.bmstu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.bmstu.model.Student;
import ru.bmstu.model.UserRole;
import ru.bmstu.repository.StudentRepository;
import ru.bmstu.repository.impl.JournalServiceImpl;
import ru.bmstu.service.impl.StudentServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private JournalServiceImpl journalService;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testStudent = new Student(studentRepository.getNewId(), "Alex", "Black", 10);
    }

    @Test
    void getAllStudentsTest() {
        List<Student> expectedStudents = Arrays.asList(
                new Student(studentRepository.getNewId(), "N1", "S1", 1),
                new Student(studentRepository.getNewId(), "N2", "S2", 2)
        );
        when(studentRepository.findAll()).thenReturn(expectedStudents);

        List<Student> actualStudents = studentService.getAllStudents();

        assertEquals(expectedStudents, actualStudents);
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void addStudentTest() {
        String firstName = "Alex";
        String lastName = "Black";
        int tokens = 10;
        UserRole role = UserRole.TEACHER;

        studentService.addStudent(firstName, lastName, tokens);

        verify(studentRepository, times(1)).add(argThat(student ->
                student.getFirstName().equals(firstName) &&
                        student.getLastName().equals(lastName) &&
                        student.getTokens() == tokens));
    }

    @Test
    void updateTokensTest() {
        int initialTokens = testStudent.getTokens();
        int change = 5;
        UserRole role = UserRole.TEACHER;

        studentService.updateTokens(testStudent, change);

        assertEquals(initialTokens + change, testStudent.getTokens());
    }

    @Test
    void expelStudentTest() {
        UserRole role = UserRole.TEACHER;

        studentService.expelStudent(testStudent);

        verify(studentRepository, times(1)).delete(testStudent);
    }
}