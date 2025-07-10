package com.parameta.apisoap.converter;

import org.springframework.stereotype.Component;
import com.parameta.apisoap.model.Employee;
import com.parameta.apisoap.wsdl.SaveEmployeeRequest;
import com.parameta.apisoap.wsdl.SaveEmployeeResponse;

@Component
public class EmployeeConverter {

    public Employee convertRequestToEntity(SaveEmployeeRequest request) {
        return new Employee(
                request.getFirstName(),
                request.getLastName(),
                request.getDocumentType(),
                request.getDocumentNumber(),
                request.getBirthDate(),
                request.getJoinDate(),
                request.getPosition(),
                request.getSalary());
    }

    public SaveEmployeeResponse convertEntityToResponse(Employee employee) {
        SaveEmployeeResponse response = new SaveEmployeeResponse();
        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setDocumentType(employee.getDocumentType());
        response.setDocumentNumber(employee.getDocumentNumber());
        response.setBirthDate(employee.getBirthDate());
        response.setJoinDate(employee.getJoinDate());
        response.setPosition(employee.getPosition());
        response.setSalary(employee.getSalary());
        return response;
    }
}
