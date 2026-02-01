package com.hardik.shift.controller;

import com.hardik.shift.entity.Employee;
import com.hardik.shift.service.EmployeeService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setUserId(request.getUserId());
        employee.setName(request.getName());
        employee.setStatus(request.getStatus());
        
        return ResponseEntity.ok(employeeService.createEmployee(employee, request.getTeamId()));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Data
    static class CreateEmployeeRequest {
        private Long userId;
        private String name;
        private Long teamId;
        private String status;
    }
}
