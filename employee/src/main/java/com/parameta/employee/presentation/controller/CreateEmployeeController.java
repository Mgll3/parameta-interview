package com.parameta.employee.presentation.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.parameta.employee.application.dto.CreateEmployeeRequest;
import com.parameta.employee.application.ports.in.CreateEmployeeService;
import com.parameta.employee.presentation.response.CreateEmployeeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/employees")
public class CreateEmployeeController {

    @Autowired
    private CreateEmployeeService createEmployeeService;

    @Operation(summary = "Create new Employee", description = "End Point to create a new employee in the system", tags = {
            "Employees" })
    @Parameter(name = "createEmployeeRequest", description = "Query parameters for creating a new employee"
            + "(all fields must be provided as query params)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/create")
    public Mono<ResponseEntity<CreateEmployeeResponse>> createEmployee(
            @Valid @ModelAttribute CreateEmployeeRequest createEmployeeRequest) {

        return createEmployeeService.execute(Mono.just(createEmployeeRequest))
                .map(savedEmployee -> {
                    return ResponseEntity.created(
                            URI.create("/api/v1/employees/"
                                    + savedEmployee.getId()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(savedEmployee);
                });
    }

    @Operation(summary = "Create Multiple Employees", description = "End Point to create multiple employees in the system", tags = {
            "Employees" })
    @PostMapping("/bulk-create")
    public Mono<ResponseEntity<List<CreateEmployeeResponse>>> createMultipleThreads(
            @Valid @RequestBody Flux<CreateEmployeeRequest> employeeRequests) {

        return createEmployeeService.executeBulk(employeeRequests)
                .collectList()
                .map(savedEmployees -> ResponseEntity
                        .created(URI.create("/api/v1/employees/bulk-create"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(savedEmployees));
    }
}
