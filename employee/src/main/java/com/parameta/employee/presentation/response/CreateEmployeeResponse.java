package com.parameta.employee.presentation.response;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateEmployeeResponse {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String documentType;
    private final String documentNumber;
    private final LocalDate birthDate;
    private final LocalDate joinDate;
    private final String position;
    private final Double salary;
    private final PeriodInfo tenure;
    private final PeriodInfo age;
}
