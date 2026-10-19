package com.TestingApplication.TestingApp.controllers;

import com.TestingApplication.TestingApp.dto.EmployeeDTO;
import com.TestingApplication.TestingApp.exceptions.ResourceNotFoundException;
import com.TestingApplication.TestingApp.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/employees") //parent path for all the below paths
public class EmployeeController {

    private final EmployeeService employeeservice;
    private final ModelMapper modelMapper;

    public EmployeeController(EmployeeService employeeservice, ModelMapper modelMapper){
        this.employeeservice = employeeservice;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable(name = "employeeId") Long id){
        EmployeeDTO employeeDTO = employeeservice.getEmployeeByID(id);
        if(employeeDTO == null){
            throw new ResourceNotFoundException("employee not found");
        }
        return ResponseEntity.ok(employeeDTO);
    }
    //you can change it to shorthand

    @GetMapping()
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false, name = "inputAge") Integer age,
                                                             @RequestParam(required = false) String type){ //required = false used to make the query params optional
        return ResponseEntity.ok(employeeservice.getAllEmployees());
    }
    //now if u give age in the query parameters it doesnt work..you have to give it as inputAge

    @PostMapping
    public ResponseEntity<EmployeeDTO> postEmployee(@RequestBody EmployeeDTO inputEmployee){
        EmployeeDTO employeeDTO = employeeservice.postEmployee(inputEmployee);
        return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO inputEmployee, @PathVariable(name = "employeeId") Long id){
        EmployeeDTO employeeDTO = employeeservice.getEmployeeByID(id);
        if(employeeDTO == null){
            throw new ResourceNotFoundException("employee not found" + id);
        }
        return ResponseEntity.ok(employeeservice.updateEmployee(inputEmployee, id));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Boolean> EmployeeDelete(@PathVariable(name = "employeeId") Long id){
        EmployeeDTO employeeDTO = employeeservice.getEmployeeByID(id);
        if(employeeDTO == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employeeservice.deleteEmployee(id));
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployee(@RequestBody Map<String, Object> updates,
                                                             @PathVariable(name = "employeeId") Long id){
        EmployeeDTO employeeDTO = employeeservice.getEmployeeByID(id);
        if(employeeDTO == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employeeservice.updatePartialEmployee(updates, id));
    }
}