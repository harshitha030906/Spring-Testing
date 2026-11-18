package com.TestingApplication.TestingApp.service;

import com.TestingApplication.TestingApp.TestContainerConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//you cannot use this annotation because it only creates a bean of repository
//UnsatisfiedDependencyException: Error creating bean with name 'com.TestingApplication.TestingApp.service.EmployeeServiceTest'
@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;

    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployeeDto(){

    }
}