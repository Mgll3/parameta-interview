package com.parameta.employee.domain.entity;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Employee {
    private UUID id;
    private String firstName;
    private String lastName;
    private String documentType;
    private String documentNumber;
    private LocalDate birthDate;
    private LocalDate joinDate;
    private String position;
    private Double salary;
}
