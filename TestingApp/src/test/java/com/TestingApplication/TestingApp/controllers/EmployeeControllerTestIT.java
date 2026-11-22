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

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
class EmployeeControllerTestIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeEntity testEmployee;
    private EmployeeDTO testEmployeeDTO;

    @BeforeEach
    void setUp(){
        testEmployee = EmployeeEntity.builder()
                .employeeID(1L)
                .firstName("harshu")
                .lastName("bala")
                .email("har@gmail.com")
                .salary(10000D)
                .age(20)
                .build();

        testEmployeeDTO = EmployeeDTO.builder()
                .employeeID(1L)
                .firstName("harshu")
                .lastName("bala")
                .email("har@gmail.com")
                .salary(10000D)
                .age(20)
                .build();
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

}