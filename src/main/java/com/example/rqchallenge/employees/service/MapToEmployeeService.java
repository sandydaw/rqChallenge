package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.model.CreatedEmployeeEntity;
import com.example.rqchallenge.employees.model.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class MapToEmployeeService {
    private Employee employee;
    private CreatedEmployeeEntity createdEmployee;

    public Employee convertToEmployee(CreatedEmployeeEntity employeeEntity) {
        employee = new Employee();
        if (StringUtils.isNotEmpty(employeeEntity.getId()))
            employee.setId(employeeEntity.getId());
        if (StringUtils.isNotEmpty(employeeEntity.getName()))
            employee.setEmployee_name(employeeEntity.getName());
        if (StringUtils.isNotEmpty(employeeEntity.getSalary()))
            employee.setEmployee_salary(employeeEntity.getSalary());
        if (StringUtils.isNotEmpty(employeeEntity.getAge()))
            employee.setEmployee_age(employeeEntity.getAge());
        return employee;
    }
}
