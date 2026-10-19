package com.TestingApplication.TestingApp.repository;

import com.TestingApplication.TestingApp.entity.EmployeeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.AutoConfigureDataJpa;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeEntity employee;

    @BeforeEach
    void setUp(){
        employee = EmployeeEntity.builder()
                .firstName("harshu")
                .lastName("bala")
                .email("har@gmail.com")
                .salary(10000D)
                .age(20)
                .build();
    }

    @Test
    void testfindByEmail_whenEmailIsPresent_thenReturnEmployee() {
        //Given/Arrange
        employeeRepository.save(employee);

        // when/act
        List<EmployeeEntity> employees = employeeRepository.findByEmail(employee.getEmail());

        //then/assert
        assertThat(employees).hasSize(1);
        assertThat(employees.get(0).getEmail()).isEqualTo(employee.getEmail());
        assertThat(employees).isNotEmpty();
        assertThat(employees).isNotNull();
    }

    @Test
    void testfindByEmail_whenEmailIsNotPresent_thenReturnEmptyEmployee() {
        //given/arrange
        String email = "harshu@gmail.com";

        //act/when
        List<EmployeeEntity> employees = employeeRepository.findByEmail(email);

        //then/assert
        assertThat(employees).isEmpty();
    }
}