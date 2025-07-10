package com.parameta.employee.infraestructure.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import com.parameta.employee.application.dto.CreateEmployeeRequest;
import com.parameta.employee.application.mapper.EmployeeMapper;
import com.parameta.employee.application.ports.outbound.EmployeeSoapPersistencePort;
import com.parameta.employee.domain.entity.Employee;
import com.parameta.employee.shared.exception.SoapFaultClientException;
import com.parameta.employee.shared.utils.Constants;
import lombok.AllArgsConstructor;
import com.parameta.employee.infraestructure.soap.client.SaveEmployeeRequest;
import com.parameta.employee.infraestructure.soap.client.SaveEmployeeResponse;

@Component
@AllArgsConstructor
public class EmployeeSoapClientAdapter implements EmployeeSoapPersistencePort {

    private static final String SOAP_URI = Constants.SOAP_EMPLOYEE_SERVICE_URL;
    @Autowired
    private final EmployeeMapper employeeMapper;
    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Override
    public Employee saveEmployee(CreateEmployeeRequest employee) {

        SaveEmployeeRequest employeeToSave = employeeMapper.toEmployeeSoap(employee);
        SaveEmployeeResponse savedEmployee = (SaveEmployeeResponse) webServiceTemplate.marshalSendAndReceive(SOAP_URI,
                employeeToSave);
        if (savedEmployee == null || savedEmployee.getId() == null) {
            throw new SoapFaultClientException(Constants.SOAP_INVALID_RESPONSE);
        }
        return employeeMapper.toEmployee(savedEmployee);
    }
}
