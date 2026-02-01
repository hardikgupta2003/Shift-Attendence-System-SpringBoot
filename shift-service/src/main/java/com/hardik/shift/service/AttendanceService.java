package com.hardik.shift.service;

import com.hardik.shift.entity.Attendance;
import com.hardik.shift.entity.Employee;
import com.hardik.shift.entity.ShiftAssignment;
import com.hardik.shift.repository.AttendanceRepository;
import com.hardik.shift.repository.EmployeeRepository;
import com.hardik.shift.repository.ShiftAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ShiftAssignmentRepository shiftAssignmentRepository;
    private final EmployeeRepository employeeRepository;

    public Attendance checkIn(Long userId) {
        Employee employee = employeeRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee profile not found!"));

        LocalDate today = LocalDate.now();

        // Check if already checked in
        if (attendanceRepository.findByEmployeeIdAndAttendanceDate(employee.getId(), today).isPresent()) {
            throw new RuntimeException("Already checked in today!");
        }

        // Find assigned shift for today
        ShiftAssignment assignment = shiftAssignmentRepository.findByEmployeeIdAndShiftDate(employee.getId(), today)
                .orElseThrow(() -> new RuntimeException("No shift assigned for today!"));

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .shift(assignment.getShift())
                .attendanceDate(today)
                .checkIn(LocalDateTime.now())
                .status("PRESENT") // Initial status
                .build();

        return attendanceRepository.save(attendance);
    }

    public Attendance checkOut(Long userId) {
        Employee employee = employeeRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee profile not found!"));

        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByEmployeeIdAndAttendanceDate(employee.getId(), today)
                .orElseThrow(() -> new RuntimeException("No Check-In record found for today!"));

        if (attendance.getCheckOut() != null) {
            throw new RuntimeException("Already checked out!");
        }

        attendance.setCheckOut(LocalDateTime.now());
        // Here we could update status based on hours worked, etc.
        
        return attendanceRepository.save(attendance);
    }
}
