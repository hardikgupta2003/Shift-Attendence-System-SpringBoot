package com.hardik.shift.repository;

import com.hardik.shift.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate attendanceDate);
    List<Attendance> findByEmployeeId(Long employeeId);
}
