package com.parameta.apisoap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.parameta.apisoap.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
