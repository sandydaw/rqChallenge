package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.model.CreatedEmployeeEntity;
import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import com.example.rqchallenge.employees.service.MapToEmployeeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class EmployeeControllerImplTest {

    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MapToEmployeeService mapToEmployeeService;
    private ObjectMapper objectMapper;
    private List<Employee> employees;
    private final String BASE_URL = "/employees";

    @BeforeEach
    void setUp() {
        Employee e1 = new Employee("1", "Tiger Nixon", "320800", "61", "");
        Employee e2 = new Employee("2", "Garrett Winters", "170750", "63", "");
        employees = List.of(e1, e2);
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(employees);
        mockMvc.perform(get(BASE_URL)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$[0].id", is("1"))).
                andExpect(jsonPath("$[0].employee_name", is("Tiger Nixon"))).
                andExpect(jsonPath("$[0].employee_salary", is("320800"))).
                andExpect(jsonPath("$[0].employee_age", is("61"))).
                andExpect(jsonPath("$[1].id", is("2"))).
                andExpect(jsonPath("$[1].employee_name", is("Garrett Winters"))).
                andExpect(jsonPath("$[1].employee_age", is("63")));
    }

    @Test
    void getEmployeesByNameSearch() throws Exception {
        when(employeeService.getEmployeeByNameSearch("Garrett Winters")).thenReturn(List.of(employees.get(1)));
        mockMvc.perform(get(BASE_URL + "/search/Garrett Winters")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$[0].id", is("2"))).
                andExpect(jsonPath("$[0].employee_name", is("Garrett Winters"))).
                andExpect(jsonPath("$[0].employee_age", is("63")));
    }

    @Test
    void getEmployeeById() throws Exception {
        when(employeeService.getEmployeeById("1")).thenReturn(employees.get(0));
        mockMvc.perform(get(BASE_URL + "/1")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id", is("1"))).
                andExpect(jsonPath("$.employee_name", is("Tiger Nixon")));
    }

    @Test
    void getHighestSalaryOfEmployees() throws Exception {
        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(320800);
        mockMvc.perform(get(BASE_URL + "/highestSalary")).andExpect(status().isOk()).
                andExpect(content().string("320800"));
    }

    @Test
    void getTopTenHighestEarningEmployeeNames() throws Exception {
        List<String> highestEarningNames = List.of("emp1", "emp2", "name3", "name4", "name5", "name6", "name7", "name8", "name9", "name10");
        when(employeeService.getTopTenHighestEarnigEmploeeNames()).thenReturn(highestEarningNames);
        MvcResult mvcResult = mockMvc.perform(get(BASE_URL + "/topTenHighestEarningEmployeeNames")).andExpect(status().isOk()).andReturn();
        List<String> names = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<String>>() {
        });
        assertAll(()-> names.stream().forEach(name -> highestEarningNames.contains(name)));
    }

    @Test
    void createEmployee() throws Exception {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name","sandeep dawre");
        employeeInput.put("salary","29000");
        employeeInput.put("age","33");
        String content = objectMapper.writeValueAsString(employeeInput);

        CreatedEmployeeEntity createdEmployee  = objectMapper.convertValue(employeeInput, CreatedEmployeeEntity.class);
        when(mapToEmployeeService.convertToEmployee(createdEmployee)).thenReturn(new Employee("25","sandeep dawre","29000","33",""));
        when(employeeService.createEmployee(employeeInput)).thenReturn(createdEmployee);

        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(content)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id",is("25"))).
                andExpect(jsonPath("$.employee_name",is("sandeep dawre"))).
                andExpect(jsonPath("$.employee_age",is("33")));
    }

    @Test
    void deleteEmployeeById() throws Exception {
        when(employeeService.deleteEmployeeById("1")).thenReturn("successfully! deleted Record");
        mockMvc.perform(delete(BASE_URL+"/1")).andExpect(status().isOk());
    }
}