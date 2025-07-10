package com.parameta.employee.application.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parameta.employee.application.dto.CreateEmployeeRequest;
import com.parameta.employee.application.mapper.EmployeeMapper;
import com.parameta.employee.application.ports.in.CreateEmployeeService;
import com.parameta.employee.application.ports.out.EmployeeSoapPersistencePort;
import com.parameta.employee.domain.services.EmployeeCreationService;
import com.parameta.employee.presentation.response.CreateEmployeeResponse;
import com.parameta.employee.presentation.response.PeriodInfo;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class CreateEmployeeUseCase implements CreateEmployeeService {

    @Autowired
    private final EmployeeSoapPersistencePort employeeSoapClient;
    @Autowired
    private final EmployeeMapper employeeMapper;
    @Autowired
    private final EmployeeCreationService employeeCreationService;

    public Mono<CreateEmployeeResponse> execute(Mono<CreateEmployeeRequest> request) {

        return employeeCreationService.validateCreation(request)
                .flatMap(validRequest -> Mono.fromCallable(() -> employeeSoapClient.saveEmployee(validRequest))
                        .subscribeOn(Schedulers.boundedElastic())) // Don't block the main thread
                .map(savedEmployee -> {
                    PeriodInfo age = PeriodInfo.from(savedEmployee.getBirthDate(), LocalDate.now());
                    PeriodInfo tenure = PeriodInfo.from(savedEmployee.getJoinDate(), LocalDate.now());
                    return employeeMapper.toCreateEmployeeResponse(savedEmployee, age, tenure);
                });
    }

    public Flux<CreateEmployeeResponse> executeBulk(Flux<CreateEmployeeRequest> requests) {
        return requests
                .flatMap(request -> employeeCreationService.validateCreation(Mono.just(request))
                        .flatMap(validRequest -> Mono.fromCallable(() -> employeeSoapClient.saveEmployee(validRequest))
                                .subscribeOn(Schedulers.boundedElastic()))
                        .map(savedEmployee -> {
                            PeriodInfo age = PeriodInfo.from(savedEmployee.getBirthDate(), LocalDate.now());
                            PeriodInfo tenure = PeriodInfo.from(savedEmployee.getJoinDate(), LocalDate.now());
                            return employeeMapper.toCreateEmployeeResponse(savedEmployee, age, tenure);
                        }));
    }

}
