package com.parameta.employee.application.dto;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateEmployeeRequest {

    @Size(min = 3, max = 40, message = "firstName must be between 3 and 40 characters")
    @NotBlank(message = "firstName is mandatory")
    private String firstName;

    @Size(min = 3, max = 40, message = "lastName must be between 3 and 40 characters")
    @NotBlank(message = "lastName is mandatory")
    private String lastName;

    @NotBlank(message = "documentType is mandatory")
    private String documentType;

    @NotBlank(message = "documentNumber is mandatory")
    private String documentNumber;

    @NotNull(message = "birthDate is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull(message = "joinDate is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    @NotBlank(message = "position is mandatory")
    private String position;

    @Min(value = 0, message = "salary must be a positive number")
    @NotNull(message = "salary is mandatory")
    private Double salary;
}
