# Shift Service - API Contract

**Base URL**: `http://localhost:8082/api`

## 🔐 Authentication
All endpoints require a valid JWT Bearer Token in the `Authorization` header.
`Authorization: Bearer <TOKEN>`

---

## 👥 Teams API

### 1. Create Team
**POST** `/teams`
```json
{
  "name": "Engineering",
  "description": "Core Backend Team"
}
```
**Response (200 OK)**
```json
{
  "id": 1,
  "name": "Engineering",
  "description": "Core Backend Team"
}
```

### 2. Get All Teams
**GET** `/teams`
**Response (200 OK)**
```json
[
  { "id": 1, "name": "Engineering", ... },
  { "id": 2, "name": "HR", ... }
]
```

---

## 👤 Employees API

### 1. Create Employee Profile
**POST** `/employees`
```json
{
  "userId": 101,
  "name": "John Doe",
  "teamId": 1,
  "status": "ACTIVE"
}
```
*Note: `userId` must match an existing ID from the Auth Service.*

**Response (200 OK)**
```json
{
  "id": 1,
  "userId": 101,
  "name": "John Doe",
  "team": { "id": 1, "name": "Engineering" },
  "status": "ACTIVE",
  "createdAt": "..."
}
```

### 2. Get All Employees
**GET** `/employees`

---

## ⏰ Shifts API

### 1. Create Shift
**POST** `/shifts`
```json
{
  "name": "Morning Shift",
  "startTime": "09:00:00",
  "endTime": "18:00:00",
  "isNight": false
}
```

### 2. Assign Shift
**POST** `/shifts/assign`
```json
{
  "employeeId": 1,
  "shiftId": 1,
  "shiftDate": "2024-02-01"
}
```
*Note: The system automatically records the ID of the ADMIN user performing this action.*

**Response (200 OK)**
```json
{
  "id": 1,
  "employee": { ... },
  "shift": { ... },
  "shiftDate": "2024-02-01",
  "assignedBy": 5,
  "createdAt": "..."
}
```

---

## 📅 Attendance API

### 1. Check-In
**POST** `/attendance/check-in`
*No body required. Uses the User ID from the JWT token.*

**Response (200 OK)**
```json
{
  "id": 1,
  "employee": { ... },
  "shift": { ... },
  "attendanceDate": "2024-02-01",
  "checkIn": "2024-02-01T09:05:00",
  "checkOut": null,
  "status": "PRESENT"
}
```

### 2. Check-Out
**POST** `/attendance/check-out`
*No body required. Uses the User ID from the JWT token.*

**Response (200 OK)**
```json
{
  "id": 1,
  "checkIn": "2024-02-01T09:05:00",
  "checkOut": "2024-02-01T18:10:00",
  ...
}
```
