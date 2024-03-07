package com.example.hr_management_arpan_silwal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloApplicationTest {

    @Test
    void oneYearSalary() {

        HelloApplication x=new HelloApplication();
        assertEquals(x.oneYearSalary(1),12);
    }
}