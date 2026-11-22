package com.TestingApplication.TestingApp.controllers;

import com.TestingApplication.TestingApp.TestContainerConfiguration;
import com.TestingApplication.TestingApp.dto.EmployeeDTO;
import com.TestingApplication.TestingApp.entity.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
public class AbstractIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    EmployeeEntity testEmployee = EmployeeEntity.builder()
            .employeeID(1L)
                .firstName("harshu")
                .lastName("bala")
                .email("har@gmail.com")
                .salary(10000D)
                .age(20)
                .build();

    EmployeeDTO testEmployeeDTO = EmployeeDTO.builder()
            .employeeID(1L)
                .firstName("harshu")
                .lastName("bala")
                .email("har@gmail.com")
                .salary(10000D)
                .age(20)
                .build();
}
