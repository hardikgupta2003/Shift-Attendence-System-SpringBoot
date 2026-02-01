# Shift & Attendance Service - Backend Documentation

## 🎯 Overview
The **Shift & Attendance Service** is a dedicated microservice responsible for:
- Managing Teams and Employees.
- creating and managing Work Shifts.
- Assigning Shifts to Employees.
- Tracking Daily Attendance (Check-in/Check-out).

It is designed to work alongside the **Auth Service**, relying on JWT tokens for user authentication.

## 🛠️ Tech Stack
- **Language**: Java 17
- **Framework**: Spring Boot 3.5.8
- **Database**: PostgreSQL
- **Security**: Spring Security + JWT
- **Build Tool**: Maven

## 🗄️ Database Schema (`shift_db`)

### 1. Teams Table (`teams`)
| Column | Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | BIGSERIAL | PK | Unique Team ID |
| `name` | VARCHAR(50) | UNIQUE, NOT NULL | Team Name |
| `description` | TEXT | | Optional Description |

### 2. Employees Table (`employees`)
| Column | Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | BIGSERIAL | PK | Employee Profile ID |
| `user_id` | BIGINT | UNIQUE, NOT NULL | Link to Auth Service User |
| `name` | VARCHAR(100) | NOT NULL | Full Name |
| `team_id` | BIGINT | FK -> teams(id) | Assigned Team |
| `status` | VARCHAR(20) | | e.g. ACTIVE, INACTIVE |
| `created_at` | TIMESTAMP | | Profile creation time |

### 3. Shifts Table (`shifts`)
| Column | Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | BIGSERIAL | PK | Unique Shift ID |
| `name` | VARCHAR(50) | UNIQUE, NOT NULL | Shift Name (e.g. Morning A) |
| `start_time` | TIME | NOT NULL | Shift Start Time |
| `end_time` | TIME | NOT NULL | Shift End Time |
| `is_night` | BOOLEAN | | Is it a night shift? |

### 4. Shift Assignments Table (`shift_assignments`)
| Column | Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | BIGSERIAL | PK | Assignment ID |
| `employee_id` | BIGINT | FK -> employees(id) | Target Employee |
| `shift_id` | BIGINT | FK -> shifts(id) | Assigned Shift |
| `shift_date` | DATE | NOT NULL | Date of Shift |
| `assigned_by` | BIGINT | | Admin User ID who assigned |
| `created_at` | TIMESTAMP | | Assignment time |

### 5. Attendance Table (`attendance`)
| Column | Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | BIGSERIAL | PK | Attendance ID |
| `employee_id` | BIGINT | FK -> employees(id) | Employee |
| `shift_id` | BIGINT | FK -> shifts(id) | Shift worked |
| `attendance_date` | DATE | NOT NULL | Date of attendance |
| `check_in` | TIMESTAMP | | Actual Check-in Time |
| `check_out` | TIMESTAMP | | Actual Check-out Time |
| `status` | VARCHAR(20) | | PRESENT, LATE, etc. |

## 🚀 Setup & Run

### Prerequisites
- PostgreSQL running locally on port `5432`.
- Database `shift_db` created.
- **Auth Service** running on port `8081` (for generating tokens).

### Environment Variables (`application.properties`)
```properties
server.port=8082
spring.datasource.url=jdbc:postgresql://localhost:5432/shift_db
spring.datasource.username=postgres
spring.datasource.password=root
jwt.secret=<SAME_AS_AUTH_SERVICE>
jwt.expiration=86400000
```

### Run Command
```bash
mvn spring-boot:run
```
