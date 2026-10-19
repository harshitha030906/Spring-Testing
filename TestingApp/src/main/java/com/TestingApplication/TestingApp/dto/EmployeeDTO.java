package com.TestingApplication.TestingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long employeeID;

    private String firstName;

    private String lastName;

    private String email;

    private Double salary;

    private Integer age;

    private String role;

}
