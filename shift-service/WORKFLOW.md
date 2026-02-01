# Shift & Attendance Service - Workflows

This document describes the standard operational workflows for setting up and using the system.

## 1️⃣ System Setup (One-time / Admin)
Before employees can use the system, the foundation must be set up.

1.  **Create Teams**: Define the organizational structure.
    - *Action*: Call `POST /api/teams`
    - *Example*: Create "IT", "HR", "Sales" teams.
2.  **Turn Shifts**: Define standard shift timings.
    - *Action*: Call `POST /api/shifts`
    - *Example*: Create "Morning (9-6)", "Night (10-7)".

## 2️⃣ Employee Onboarding
When a new user registers in the Auth Service, their employee profile must be created here.

1.  **User Registration**: User signs up in Auth Service -> Gets `userId`.
2.  **Create Profile**: Admin creates an employee record linking the `userId` to a `Team`.
    - *Action*: Call `POST /api/employees` with `userId` and `teamId`.

## 3️⃣ Shift Assignment (Weekly/Daily)
Employees need to know when to work.

1.  **Assign Shift**: Admin assigns a specific shift to an employee for a specific date.
    - *Action*: Call `POST /api/shifts/assign`
    - *Input*: `employeeId`, `shiftId`, `shiftDate`.
    - *Result*: The system records the assignment. Only employees with an assigned shift can check in.

## 4️⃣ Daily Attendance (Employee Action)
The daily routine for an employee.

1.  **Check-In**: Employee arrives at work.
    - *Prerequisite*: Must have a shift assigned for today.
    - *Action*: Call `POST /api/attendance/check-in` (Token required).
    - *System Logic*: Verifies assignment, records timestamp, sets status to `PRESENT`.
2.  **Work**: Employee performs their duties.
3.  **Check-Out**: Employee leaves work.
    - *Action*: Call `POST /api/attendance/check-out`.
    - *System Logic*: Updates the record with check-out time.

## 5️⃣ Exception Flows
- **No Shift Assigned**: If an employee tries to check in without an assignment, the system throws an error.
- **Double Check-In**: Trying to check in twice on the same day results in an error.
- **Forgot Check-Out**: Currently, the system leaves the `check_out` field null (future scope: auto-close jobs).
