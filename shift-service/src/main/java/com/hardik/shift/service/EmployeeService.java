package com.hardik.shift.service;

import com.hardik.shift.entity.Employee;
import com.hardik.shift.entity.Team;
import com.hardik.shift.repository.EmployeeRepository;
import com.hardik.shift.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;

    public Employee createEmployee(Employee employee, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        employee.setTeam(team);
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByUserId(Long userId) {
        return employeeRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found for userId: " + userId));
    }
    
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
}
