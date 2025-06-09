package ru.bmstu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Data
@Entity
public class Student {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private int tokens;

    public Student(long id, String firstName, String lastName, int tokens) {
        this.id = id;
        this.firstName = firstName;
        this.lastName =lastName;
        this.tokens = tokens;
    }

    public Student() {
    }
}