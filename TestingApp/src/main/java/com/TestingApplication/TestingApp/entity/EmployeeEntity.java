package com.TestingApplication.TestingApp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeEntity {
    @Id
    private Long employeeID;
    private String firstName;
    private String lastName;
    private String email;
    private Double salary;
    private Integer age;
}
