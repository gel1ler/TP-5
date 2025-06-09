package ru.bmstu.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.aspect.RoleCheckAspect;
import ru.bmstu.model.Student;
import ru.bmstu.model.UserRole;
import ru.bmstu.service.StudentService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;
    private final RoleCheckAspect roleCheckAspect;

//    public StudentController(StudentService studentService) {
//        this.studentService = studentService;
//    }

    @Operation(
            summary = "Получить всех студентов",
            description = "Возвращает список всех студентов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список студентов",
                            content = {}
                    )
            }
    )
    @GetMapping("/")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @Operation(
            summary = "Получить студента по ID",
            description = "Возвращает студента по ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Студент",
                            content = {}
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        Optional<Student> student = studentService.getById(id);

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(
            summary = "Добавить студента",
            description = "Добавляет студента",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлено",
                            content = {}
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(
            @RequestBody Student student,
            @RequestHeader("X-User-Role") UserRole userRole) {
        roleCheckAspect.setCurrentUserRole(userRole);

        studentService.addStudent(student);
        return ResponseEntity.ok(student);
    }

    @PatchMapping("/{id}/tokens")
    public ResponseEntity<Void> updateTokens(
            @PathVariable("id") Long id,
            @RequestParam("change") int change,
            @RequestHeader("X-User-Role") UserRole userRole) {
        roleCheckAspect.setCurrentUserRole(userRole);

        studentService.updateTokens(id, change);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> expelStudent(
            @PathVariable("id") Long id,
            @RequestHeader("X-User-Role") UserRole userRole) {
        roleCheckAspect.setCurrentUserRole(userRole);

        studentService.expelStudent(id);
        return ResponseEntity.ok().build();
    }
}
