package com.parameta.employee.application.ports.in;

import com.parameta.employee.application.dto.CreateEmployeeRequest;
import com.parameta.employee.presentation.response.CreateEmployeeResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreateEmployeeService {

    public Mono<CreateEmployeeResponse> execute(Mono<CreateEmployeeRequest> request);

    public Flux<CreateEmployeeResponse> executeBulk(Flux<CreateEmployeeRequest> requests);

}
