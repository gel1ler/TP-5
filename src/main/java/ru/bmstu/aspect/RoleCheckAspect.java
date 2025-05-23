// RoleCheckAspect.java
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

    @Before("execution(* ru.bmstu.studenttokens.service.StudentService.addStudent(..)) && args(.., role)")
    public void checkAddStudentPermission(UserRole role) {
        if (role != UserRole.TEACHER) {
            throw new SecurityException("Only teachers can add students");
        }
    }

    @Before("execution(* ru.bmstu.studenttokens.service.StudentService.updateTokens(..)) && args(.., change, role)")
    public void checkUpdateTokensPermission(int change, UserRole role) {
        if (role != UserRole.TEACHER) {
            throw new SecurityException("Only teachers can update tokens");
        }
    }

    @Before("execution(* ru.bmstu.studenttokens.service.StudentService.expelStudent(..)) && args(.., role)")
    public void checkExpelStudentPermission(UserRole role) {
        if (role != UserRole.TEACHER) {
            throw new SecurityException("Only teachers can expel students");
        }
    }
}