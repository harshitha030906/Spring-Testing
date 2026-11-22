package com.TestingApplication.TestingApp.controllers;

import com.TestingApplication.TestingApp.TestContainerConfiguration;
import com.TestingApplication.TestingApp.dto.EmployeeDTO;
import com.TestingApplication.TestingApp.entity.EmployeeEntity;
import com.TestingApplication.TestingApp.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class EmployeeControllerTestIT extends AbstractIntegrationTest{

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp(){
        employeeRepository.deleteAll();
    }

    @Test
    void testGetEmployeeById_success(){
        EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);
        webTestClient.get()
                .uri("/employees/{employeeId}", savedEmployee.getEmployeeID())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDTO.class)
               // .isEqualTo(testEmployeeDTO);
                .value(employeeDTO -> {
                    assertThat(employeeDTO.getEmail()).isEqualTo(savedEmployee.getEmail());
                });
    }

    @Test
    void testGetEmployeeById_Failure(){
        webTestClient.get()
                .uri("/employees/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateEmployee_whenEmployeeNotPresent_thenCreateEmployee(){
        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployeeDTO.getEmail())
                .jsonPath("$.firstName").isEqualTo(testEmployeeDTO.getFirstName());
    }

    @Test
    void testUpdateEmployee_whenEmployeeNotPresent_thenThrowException(){
        webTestClient.put()
                .uri("/employees/999")
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateEmployee_whenEmployeePresent_thenUpdateEmployee(){
        EmployeeEntity toBeUpdatedEmployee = employeeRepository.save(testEmployee);
        testEmployeeDTO.setFirstName("Random");
        testEmployeeDTO.setLastName("Random LastName");

        webTestClient.put()
                .uri("/employees/1")
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                //.isEqualTo(testEmployeeDTO);
                .jsonPath("$.firstName").isEqualTo(testEmployeeDTO.getFirstName())
                .jsonPath("$.lastName").isEqualTo(testEmployeeDTO.getLastName());

    }

    @Test
    void testDeleteEmployee_whenEmployeeNotPresent_thenthrowException(){
        webTestClient.delete()
                .uri("/employees/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteEmployee_whenEmployeePresent_thenDeleteEmployee(){
        EmployeeEntity deleteEmployee = employeeRepository.save(testEmployee);

        webTestClient.delete()
                .uri("/employees/{id}", deleteEmployee.getEmployeeID())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class);

        webTestClient.delete()
                .uri("/employees/{id}", deleteEmployee.getEmployeeID())
                .exchange()
                .expectStatus().isNotFound();
    }
}