package com.TestingApplication.TestingApp.service;

import com.TestingApplication.TestingApp.TestContainerConfiguration;
import com.TestingApplication.TestingApp.dto.EmployeeDTO;
import com.TestingApplication.TestingApp.entity.EmployeeEntity;
import com.TestingApplication.TestingApp.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


//@DataJpaTest
//you cannot use this annotation because it only creates a bean of repository
//UnsatisfiedDependencyException: Error creating bean with name 'com.TestingApplication.TestingApp.service.EmployeeServiceTest'
@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    //@Mock is not used here because modelmapper is a third party library and already tested
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployeeDto(){
        //assign
        Long id = 1L;
        EmployeeEntity employee = EmployeeEntity.builder()
                .employeeID(id)
                .firstName("harshu")
                .lastName("bala")
                .email("harshitha@gmail.com")
                .salary(200D)
                .age(18)
                .build();
        when(employeeRepository.existsById(id)).thenReturn(true);
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        //act
        EmployeeDTO employeeDTO = employeeService.getEmployeeByID(id);
        //assert
        assertThat(employeeDTO.getEmployeeID()).isEqualTo(id);
        assertThat(employeeDTO.getFirstName()).isEqualTo("harshu");
        assertThat(employeeDTO.getLastName()).isEqualTo("bala");

        verify(employeeRepository).findById(id); //used to verify whether findByID method is called in employeeRepository or not
    }
}