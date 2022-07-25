package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.model.CreatedEmployeeEntity;
import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import com.example.rqchallenge.employees.service.MapToEmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeControllerImpl implements IEmployeeController {
    private final Logger logger = LoggerFactory.getLogger(EmployeeControllerImpl.class);
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private MapToEmployeeService mapToEmployeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        logger.info("Inside getAllEmployees");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        logger.info("Inside getEmployeeByNameSearch. Search string {}", searchString);
        return ResponseEntity.ok(employeeService.getEmployeeByNameSearch(searchString));
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        logger.info("Inside getEmployeeById. Searching for id {}", id);
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        logger.info("Inside getHighestSalaryOfEmployee");
        return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        logger.info("Inside getTopTenHighestEarningEmployeeNames");
        return ResponseEntity.ok(employeeService.getTopTenHighestEarnigEmploeeNames());
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) throws JsonProcessingException {
        logger.info("Inside create employee with input request data {}", employeeInput);
        CreatedEmployeeEntity employee = employeeService.createEmployee(employeeInput);
        Employee createdEmployee = mapToEmployeeService.convertToEmployee(employee);
        return ResponseEntity.ok(createdEmployee);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        logger.info("Insdie deleteEmployeeById. Trying to delete employee with id {}", id);
        return ResponseEntity.ok(employeeService.deleteEmployeeById(id));
    }
}
