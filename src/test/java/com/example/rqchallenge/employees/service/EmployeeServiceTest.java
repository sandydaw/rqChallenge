package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.RqChallengeApplication;
import com.example.rqchallenge.constant.RqAppConstant;
import com.example.rqchallenge.employees.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RqChallengeApplication.class)
class EmployeeServiceTest {

    private final String BASE_URL = "http://localhost:8080";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;
    private List<Employee> employees;
    private MockRestServiceServer mockRestServiceServer;
    @Mock
    TestService testService;

    @BeforeEach
    void setUp() {
        Employee e1 = new Employee("1", "Tiger Nixon", "320800", "61", "");
        Employee e2 = new Employee("2", "Garrett Winters", "170750", "63", "");
        employees = List.of(e1, e2);
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getAllEmployees() throws JsonProcessingException {
        EmployeesListResponse employeesListResponse = new EmployeesListResponse();
        employeesListResponse.setMessage("Successfully! All records has been fetched.");
        employeesListResponse.setData(employees);
        employeesListResponse.setStatus("success");
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(BASE_URL + RqAppConstant.GET_ALL_EMPLOYEES_ENDPOINT)).
                andExpect(method(HttpMethod.GET)).
                andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(employeesListResponse)));
        List<Employee> allEmployees = employeeService.getAllEmployees();
        assertAll(() -> assertEquals(employees.size(), allEmployees.size()),
                () -> assertEquals(employees.get(0).getEmployee_name(), allEmployees.get(0).getEmployee_name()),
                () -> assertEquals(employees.get(0).getEmployee_age(), allEmployees.get(0).getEmployee_age()),
                () -> assertEquals(employees.get(0).getId(), allEmployees.get(0).getId()),
                () -> assertEquals((employees.get(1).getId()), allEmployees.get(1).getId()));
    }

    @Test
    void getEmployeeById() throws JsonProcessingException, URISyntaxException {
        Employee emp = new Employee("1", "sandeep dawre", "2000", "33", "");
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setStatus("success");
        employeeResponse.setData(emp);
        employeeResponse.setMessage("Successfully! Record has been fetched.");
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI(BASE_URL + RqAppConstant.GET_EMPLOYEE_BY_ID_ENDPOINT + "/1"))).
                andExpect(method(HttpMethod.GET)).
                andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(employeeResponse)));
        Employee employeeResp = employeeService.getEmployeeById("1");
        assertAll(() -> assertEquals(emp.getId(), employeeResp.getId()),
                () -> assertEquals(emp.getEmployee_age(), employeeResp.getEmployee_age()),
                () -> assertEquals(emp.getEmployee_name(), emp.getEmployee_name()));
    }

    @Test
    void getEmployeeByNameSearch() {
        //mockRestServiceServer.expect(ExpectedCount.once(),requestTo(BASE_URL+RqAppConstant.))
    }

    @Test
    void getHighestSalaryOfEmployees() {
        Employee  employee = Mockito.mock(Employee.class);

        when(testService.getEmployee("1")).thenReturn(employee);
        Employee employee1 = employeeService.testMethod("1");
        System.out.println(employee1);
    }

    @Test
    void getTopTenHighestEarningEmployeeNames() {
    }

    @Test
    void createEmployee() throws JsonProcessingException {
        Map<String, Object> inputEmployee = new HashMap<>();
        inputEmployee.put("name", "Sandeep Dawre");
        inputEmployee.put("salary", "333000");
        inputEmployee.put("age", "33");
        String empJasonPayload = objectMapper.writeValueAsString(inputEmployee);
        inputEmployee.put("id", "101");
        Map<String, Object> createdEmployeeResponse = new HashMap<>();
        createdEmployeeResponse.put("status", "success");
        createdEmployeeResponse.put("data", inputEmployee);

        String empJasonResponsePayload = objectMapper.writeValueAsString(createdEmployeeResponse);
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(BASE_URL + RqAppConstant.CREATE_EMPLOYEE_ENDPOINT)).
                andExpect(method(HttpMethod.POST)).
                andExpect(content().json(empJasonPayload)).andExpect(content().contentType(MediaType.APPLICATION_JSON)).
                andRespond(withStatus(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).
                        body(empJasonResponsePayload));
        CreatedEmployeeEntity employeeResponse = employeeService.createEmployee(inputEmployee);
        assertAll(() -> assertEquals(inputEmployee.get("name"), employeeResponse.getName()),
                () -> assertEquals(inputEmployee.get("salary"), employeeResponse.getSalary()),
                () -> assertEquals(inputEmployee.get("age"), employeeResponse.getAge()),
                () -> assertNotNull(employeeResponse.getId()));
    }

    @Test
    void deleteEmployeeById() throws JsonProcessingException {
        DeletedEmployeeEntity deletedEmployeeEntity = new DeletedEmployeeEntity();
        deletedEmployeeEntity.setMessage("successfully! deleted Record");
        deletedEmployeeEntity.setStatus("success");
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(BASE_URL + RqAppConstant.DELETE_EMPLOYEE_ENDPOINT + "/1")).
                andExpect(method(HttpMethod.DELETE)).
                andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(deletedEmployeeEntity)));
        String status = employeeService.deleteEmployeeById("1");
        assertEquals("success", status);
    }
}