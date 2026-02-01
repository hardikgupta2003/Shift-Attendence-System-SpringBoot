package com.hardik.shift.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "shift_assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;

    @Column(name = "shift_date", nullable = false)
    private LocalDate shiftDate;

    @Column(name = "assigned_by")
    private Long assignedBy; // Admin User ID

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
