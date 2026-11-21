package com.TestingApplication.TestingApp.service;

import com.TestingApplication.TestingApp.dto.EmployeeDTO;
import com.TestingApplication.TestingApp.entity.EmployeeEntity;
import com.TestingApplication.TestingApp.exceptions.ResourceNotFoundException;
import com.TestingApplication.TestingApp.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper){
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public boolean isExists(Long id){
        return employeeRepository.existsById(id);
    }

    public EmployeeDTO getEmployeeByID(Long id){
        log.info("request to get employee by id {}", id);
        if(isExists(id)){
            log.info("employee found with id {}", id);
            return modelMapper.map(employeeRepository.findById(id), EmployeeDTO.class);
        }
        log.error("Employee not found with id {}", id);
        throw new ResourceNotFoundException("employee not found");
    }

    public List<EmployeeDTO> getAllEmployees(){
        log.info("Request to get all employees");
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        log.info("employee entities {}", employeeEntities);
        return employeeEntities.stream().
                map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO postEmployee(EmployeeDTO inputEmployee){
        log.info("request to post employee {}", inputEmployee);
        EmployeeEntity saveEmployeeEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity employeeEntity = employeeRepository.save(saveEmployeeEntity);
        log.info("employee saved in the repository {}", employeeEntity);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(EmployeeDTO inputEmployee, Long id){
        log.info("request to update employee {}", inputEmployee);
        EmployeeEntity employeeEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        employeeEntity.setEmployeeID(id);
        EmployeeEntity updatedEmployee = employeeRepository.save(employeeEntity);
        log.info("employee updated in the repository {}", updatedEmployee);
        return modelMapper.map(updatedEmployee, EmployeeDTO.class);
    }

    public boolean deleteEmployee(Long id){
        if(isExists(id)){
            employeeRepository.deleteById(id);
            return true;
        }
        throw new ResourceNotFoundException("employee not found");
    }

    public EmployeeDTO updatePartialEmployee(Map<String,Object> updates, Long id){
        log.info("request to update employee {}", updates);
        if(isExists(id)){
            EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
            updates.forEach((field, value) -> {
                Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class, field);
                fieldToBeUpdated.setAccessible(true);
                ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
            });
            EmployeeEntity updatedEmployee = employeeRepository.save(employeeEntity);
            log.info("employee updated in the repository {}", updatedEmployee);
            return modelMapper.map(updatedEmployee, EmployeeDTO.class);
        }
        log.error("Employee not found with id {}", id);
        throw new ResourceNotFoundException("employee not found");
    }

}
