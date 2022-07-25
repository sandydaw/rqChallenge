package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.constant.RqAppConstant;
import com.example.rqchallenge.employees.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.rqchallenge.constant.RqAppConstant.GET_ALL_EMPLOYEES_ENDPOINT;

@Service
public class EmployeeService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    TestService  testService;
    public List<Employee> getAllEmployees() {

        ResponseEntity<EmployeesListResponse> listResponseEntity = restTemplate.exchange(GET_ALL_EMPLOYEES_ENDPOINT, HttpMethod.GET, new HttpEntity<>(getHeaders()), EmployeesListResponse.class);
        return listResponseEntity.getBody().getData();
    }

    public Employee getEmployeeById(String id) {
        HttpEntity requestHeader = new HttpEntity(getHeaders());
        ResponseEntity<EmployeeResponse> employeeResponseEntity = restTemplate.exchange(RqAppConstant.GET_EMPLOYEE_BY_ID_ENDPOINT + "/" + id, HttpMethod.GET, requestHeader, EmployeeResponse.class);
        return employeeResponseEntity.getBody().getData();
    }

    public List<Employee> getEmployeeByNameSearch(String searchName) {
        return null;
    }

    public Integer getHighestSalaryOfEmployees() {
        return null;
    }

    public List<String> getTopTenHighestEarnigEmploeeNames() {
        return null;
    }

    public CreatedEmployeeEntity createEmployee(Map<String, Object> employeeInput) throws JsonProcessingException {
        HttpHeaders headers  = getHeaders();
        HttpEntity  httpEntity = new HttpEntity(employeeInput,headers);
       ResponseEntity<CreateEmployeeResponse> responseEntity = restTemplate.exchange(RqAppConstant.CREATE_EMPLOYEE_ENDPOINT, HttpMethod.POST, httpEntity, CreateEmployeeResponse.class);
       return responseEntity.getBody().getData();
    }

    public String deleteEmployeeById(String employeeId) {
        HttpEntity httpEntity = new HttpEntity(getHeaders());
        ResponseEntity<DeletedEmployeeEntity> deletedEmployeeEntityResponseEntity = restTemplate.exchange(RqAppConstant.DELETE_EMPLOYEE_ENDPOINT + "/" + employeeId, HttpMethod.DELETE, httpEntity, DeletedEmployeeEntity.class);
        return deletedEmployeeEntityResponseEntity.getBody().getStatus();
    }
    public Employee testMethod(String id ){
        Employee employee = testService.getEmployee(id);
        return employee;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
