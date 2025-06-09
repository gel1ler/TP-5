package ru.bmstu.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ru.bmstu.model.UserRole;

@Aspect
@Component
public class RoleCheckAspect {
    private UserRole currentUserRole = UserRole.STUDENT;

    public void setCurrentUserRole(UserRole role) {
        this.currentUserRole = role;
    }

    @Before("execution(* ru.bmstu.service.StudentService.addStudent(..))")
    public void checkAddStudentPermission() {
        if (currentUserRole != UserRole.TEACHER) {
            throw new SecurityException("Only teachers can add students");
        }
    }

    @Before("execution(* ru.bmstu.service.StudentService.updateTokens(..))")
    public void checkUpdateTokensPermission() {
        if (currentUserRole != UserRole.TEACHER) {
            throw new SecurityException("Only teachers can update tokens");
        }
    }

    @Before("execution(* ru.bmstu.service.StudentService.expelStudent(..))")
    public void checkExpelStudentPermission() {
        if (currentUserRole != UserRole.TEACHER) {
            throw new SecurityException("Only teachers can expel students");
        }
    }
}