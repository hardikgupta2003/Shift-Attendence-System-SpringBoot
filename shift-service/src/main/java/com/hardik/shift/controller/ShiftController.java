package com.hardik.shift.controller;

import com.hardik.shift.entity.Shift;
import com.hardik.shift.entity.ShiftAssignment;
import com.hardik.shift.service.ShiftService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;

    @PostMapping
    public ResponseEntity<Shift> createShift(@RequestBody Shift shift) {
        return ResponseEntity.ok(shiftService.createShift(shift));
    }

    @GetMapping
    public ResponseEntity<List<Shift>> getAllShifts() {
        return ResponseEntity.ok(shiftService.getAllShifts());
    }

    @PostMapping("/assign")
    public ResponseEntity<ShiftAssignment> assignShift(@RequestBody AssignShiftRequest request) {
        // Extract Admin User ID from Security Context (who is assigning)
        Long assignedBy = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        return ResponseEntity.ok(shiftService.assignShift(
                request.getEmployeeId(),
                request.getShiftId(),
                request.getShiftDate(),
                assignedBy
        ));
    }

    @Data
    static class AssignShiftRequest {
        private Long employeeId;
        private Long shiftId;
        private LocalDate shiftDate;
    }
}
