package com.example.rqchallenge.employees.model;

public class EmployeeResponse {
    private String status;
    private Employee data;
    private String message;

    public String getStatus() {
        return status;
    }

    public Employee getData() {
        return data;
    }

    public void setData(Employee data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
