package com.example.hr_management_arpan_silwal.Model;

public class SalaryORM{
    private String employeeNameORM;
    private int SIdORM;
    private int UIdORM;
    private double salaryAmountORM;

    public SalaryORM(int SIdORM, int SIdORM1, double salaryAmountORM) {
        this.SIdORM = SIdORM;
        this.UIdORM = SIdORM1;
        this.salaryAmountORM = salaryAmountORM;
    }
    // Overloaded constructor
    public SalaryORM(int sidORM, String employeeNameORM, double salaryAmountORM) {
        this.SIdORM = sidORM;
        this.employeeNameORM = employeeNameORM;
        this.salaryAmountORM = salaryAmountORM;
    }

    public String getEmployeeNameORM() {
        return employeeNameORM;
    }

    public void setEmployeeNameORM(String employeeNameORM) {
        this.employeeNameORM = employeeNameORM;
    }

    public int getSIdORM() {
        return SIdORM;
    }

    public void setSIdORM(int SIdORM) {
        this.SIdORM = SIdORM;
    }

    public int getUIdORM() {
        return UIdORM;
    }

    public void setUIdORM(int UIdORM) {
        this.UIdORM = UIdORM;
    }

    public double getSalaryAmountORM() {
        return salaryAmountORM;
    }

    public void setSalaryAmountORM(double salaryAmountORM) {
        this.salaryAmountORM = salaryAmountORM;
    }
}
