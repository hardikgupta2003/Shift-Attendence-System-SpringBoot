package com.hardik.shift.service;

import com.hardik.shift.entity.Employee;
import com.hardik.shift.entity.Shift;
import com.hardik.shift.entity.ShiftAssignment;
import com.hardik.shift.repository.EmployeeRepository;
import com.hardik.shift.repository.ShiftAssignmentRepository;
import com.hardik.shift.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftService {

    private final ShiftRepository shiftRepository;
    private final ShiftAssignmentRepository shiftAssignmentRepository;
    private final EmployeeRepository employeeRepository;

    public Shift createShift(Shift shift) {
        return shiftRepository.save(shift);
    }

    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    public ShiftAssignment assignShift(Long employeeId, Long shiftId, LocalDate date, Long assignedBy) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        
        ShiftAssignment assignment = ShiftAssignment.builder()
                .employee(employee)
                .shift(shift)
                .shiftDate(date)
                .assignedBy(assignedBy)
                .build();
        
        return shiftAssignmentRepository.save(assignment);
    }
}
