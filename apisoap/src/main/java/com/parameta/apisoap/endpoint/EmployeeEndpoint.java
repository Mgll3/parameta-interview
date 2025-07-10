package com.parameta.apisoap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.parameta.apisoap.converter.EmployeeConverter;
import com.parameta.apisoap.model.Employee;
import com.parameta.apisoap.wsdl.SaveEmployeeRequest;
import com.parameta.apisoap.wsdl.SaveEmployeeResponse;
import com.parameta.apisoap.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Endpoint
@RequiredArgsConstructor
public class EmployeeEndpoint {

    private static final String NAMESPACE_URI = "http://parameta.com/apisoap/employee";

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final EmployeeConverter employeeConverter;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveEmployeeRequest")
    @ResponsePayload
    public SaveEmployeeResponse saveEmployee(@RequestPayload SaveEmployeeRequest request) {

        Employee employee = employeeConverter.convertRequestToEntity(request);

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeConverter.convertEntityToResponse(savedEmployee);

    }
}
