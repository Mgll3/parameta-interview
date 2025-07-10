package com.parameta.employee.domain.services;

import java.time.LocalDate;
import java.time.Period;
import org.springframework.stereotype.Service;
import com.parameta.employee.application.dto.CreateEmployeeRequest;
import com.parameta.employee.shared.utils.Constants;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmployeeCreationService {

    public Mono<CreateEmployeeRequest> validateCreation(Mono<CreateEmployeeRequest> employeeMono) {
        return employeeMono
                .filter(request -> isAdult(request.getBirthDate()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ERROR_EMPLOYEE_MIN_AGE)));
    }

    public boolean isAdult(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        int age = Period.between(birthDate, today).getYears();
        return age >= 18;
    }
}
