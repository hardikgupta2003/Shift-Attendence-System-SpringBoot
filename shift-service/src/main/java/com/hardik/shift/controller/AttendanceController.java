package com.hardik.shift.controller;

import com.hardik.shift.entity.Attendance;
import com.hardik.shift.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/check-in")
    public ResponseEntity<Attendance> checkIn() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(attendanceService.checkIn(userId));
    }

    @PostMapping("/check-out")
    public ResponseEntity<Attendance> checkOut() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(attendanceService.checkOut(userId));
    }
}
