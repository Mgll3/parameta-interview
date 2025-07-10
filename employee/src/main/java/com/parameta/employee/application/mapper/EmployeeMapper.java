package com.parameta.employee.application.mapper;

import org.springframework.stereotype.Component;

import com.parameta.employee.application.dto.CreateEmployeeRequest;
import com.parameta.employee.domain.entity.Employee;
import com.parameta.employee.infraestructure.soap.client.SaveEmployeeRequest;
import com.parameta.employee.infraestructure.soap.client.SaveEmployeeResponse;
import com.parameta.employee.presentation.response.CreateEmployeeResponse;
import com.parameta.employee.presentation.response.PeriodInfo;

@Component
public class EmployeeMapper {

    public CreateEmployeeResponse toCreateEmployeeResponse(Employee employee, PeriodInfo age, PeriodInfo tenure) {
        return new CreateEmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDocumentType(),
                employee.getDocumentNumber(),
                employee.getBirthDate(),
                employee.getJoinDate(),
                employee.getPosition(),
                employee.getSalary(),
                tenure,
                age);
    }

    public SaveEmployeeRequest toEmployeeSoap(CreateEmployeeRequest request) {
        SaveEmployeeRequest saveEmployeeRequest = new SaveEmployeeRequest();
        saveEmployeeRequest.setFirstName(request.getFirstName());
        saveEmployeeRequest.setLastName(request.getLastName());
        saveEmployeeRequest.setDocumentType(request.getDocumentType());
        saveEmployeeRequest.setDocumentNumber(request.getDocumentNumber());
        saveEmployeeRequest.setBirthDate(request.getBirthDate().toString());
        saveEmployeeRequest.setJoinDate(request.getJoinDate().toString());
        saveEmployeeRequest.setPosition(request.getPosition());
        saveEmployeeRequest.setSalary(request.getSalary());
        return saveEmployeeRequest;
    }

    public Employee toEmployee(SaveEmployeeResponse employeeSoap) {
        return new Employee(
                employeeSoap.getId() != null ? java.util.UUID.fromString(employeeSoap.getId()) : null,
                employeeSoap.getFirstName(),
                employeeSoap.getLastName(),
                employeeSoap.getDocumentType(),
                employeeSoap.getDocumentNumber(),
                employeeSoap.getBirthDate() != null ? java.time.LocalDate.parse(employeeSoap.getBirthDate()) : null,
                employeeSoap.getJoinDate() != null ? java.time.LocalDate.parse(employeeSoap.getJoinDate()) : null,
                employeeSoap.getPosition(),
                employeeSoap.getSalary());
    }

}
