package com.example.rqchallenge.employees.model;

public class CreateEmployeeResponse {
    private String message;
    private String status;
    private CreatedEmployeeEntity data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CreatedEmployeeEntity getData() {
        return data;
    }

    public void setData(CreatedEmployeeEntity data) {
        this.data = data;
    }
}
