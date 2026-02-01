package com.hardik.shift.repository;

import com.hardik.shift.entity.ShiftAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftAssignmentRepository extends JpaRepository<ShiftAssignment, Long> {
    Optional<ShiftAssignment> findByEmployeeIdAndShiftDate(Long employeeId, LocalDate shiftDate);
    List<ShiftAssignment> findByEmployeeId(Long employeeId);
}
