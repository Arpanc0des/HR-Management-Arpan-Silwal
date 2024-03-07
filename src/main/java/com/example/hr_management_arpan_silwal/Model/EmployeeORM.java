package com.example.hr_management_arpan_silwal.Model;

public class EmployeeORM {
    private int employeeIdORM;
    private String employeeNameORM;
    private String employeeEmailORM;
    private String employeeTitleORM;

    public EmployeeORM(int employeeIdORM, String employeeNameORM, String employeeEmailORM, String employeeTitleORM) {
        this.employeeIdORM = employeeIdORM;
        this.employeeNameORM = employeeNameORM;
        this.employeeEmailORM = employeeEmailORM;
        this.employeeTitleORM = employeeTitleORM;
    }

    public int getEmployeeIdORM() {
        return employeeIdORM;
    }

    public void setEmployeeIdORM(int employeeIdORM) {
        this.employeeIdORM = employeeIdORM;
    }

    public String getEmployeeNameORM() {
        return employeeNameORM;
    }

    public void setEmployeeNameORM(String employeeNameORM) {
        this.employeeNameORM = employeeNameORM;
    }

    public String getEmployeeEmailORM() {
        return employeeEmailORM;
    }

    public void setEmployeeEmailORM(String employeeEmailORM) {
        this.employeeEmailORM = employeeEmailORM;
    }

    public String getEmployeeTitleORM() {
        return employeeTitleORM;
    }

    public void setEmployeeTitleORM(String employeeTitleORM) {
        this.employeeTitleORM = employeeTitleORM;
    }
}
