package com.TestingApplication.TestingApp.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long employeeID;

    private String firstName;

    private String lastName;

    private String email;

    private Double salary;

    private Integer age;

    private String role;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return Objects.equals(employeeID, that.employeeID) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(salary, that.salary) && Objects.equals(age, that.age) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeID, firstName, lastName, email, salary, age, role);
    }
}
