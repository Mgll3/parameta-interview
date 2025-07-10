package com.parameta.employee.application.ports.outbound;

import com.parameta.employee.application.dto.CreateEmployeeRequest;
import com.parameta.employee.domain.entity.Employee;

public interface EmployeeSoapPersistencePort {
    Employee saveEmployee(CreateEmployeeRequest employee);

}
