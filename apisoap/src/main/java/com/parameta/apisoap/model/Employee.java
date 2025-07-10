package com.parameta.apisoap.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    private String id;
    @Column(nullable = false, length = 40)
    private String firstName;
    @Column(nullable = false, length = 40)
    private String lastName;
    @Column(nullable = false)
    private String documentType;
    @Column(nullable = false)
    private String documentNumber;
    @Column(nullable = false)
    private String birthDate;
    @Column(nullable = false)
    private String joinDate;
    @Column(nullable = false)
    private String position;
    @Column(nullable = false)
    private Double salary;

    // Default constructor for JPA
    public Employee() {
    }

    public Employee(String firstName, String lastName, String documentType, String documentNumber, String birthDate,
            String joinDate, String position, Double salary) {
        this.id = java.util.UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.birthDate = birthDate;
        this.joinDate = joinDate;
        this.position = position;
        this.salary = salary;
    }
}
