package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public Employee  getEmployee(String id){
        return new Employee("1","test name","some salary","age","imager");
    }

}
