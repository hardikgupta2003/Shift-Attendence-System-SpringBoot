# Backend Documentation: Shift Attendance System (Auth Service)

## Project Overview
This is a Spring Boot-based Authentication Service responsible for user registration, login, and securing API endpoints using JWT (JSON Web Tokens).

## Technology Stack
- **Language:** Java 17
- **Framework:** Spring Boot 3.5.8
- **Database:** PostgreSQL
- **Security:** Spring Security, JWT (io.jsonwebtoken 0.11.5)
- **Tools:** Maven, Lombok

## Backend Workflow

### 1. Authentication Flow
The system uses a stateless JWT authentication mechanism:
1.  **Registration:**
    -   Endpoint: `POST /api/auth/register`
    -   Process: User submits details -> System checks if email exists -> Encrypts password -> Assigns 'USER' role -> Saves to Database.
2.  **Login:**
    -   Endpoint: `POST /api/auth/login`
    -   Process: User submits credentials -> `AuthenticationManager` verifies credentials -> `JwtUtil` generates a signed JWT -> Token returned to user.
3.  **Request Authorization:**
    -   Client sends JWT in `Authorization` header (`Bearer <token>`).
    -   `JwtAuthFilter` intercepts request -> Validates token -> Sets Authentication in SecurityContext.
    -   If valid, request proceeds to Controller; otherwise, 403 Forbidden.

### 2. Security Configuration
- **Public Endpoints:** `/api/auth/**`, `/api/public/**`
- **Secured Endpoints:** All other requests require authentication.
- **CSRF:** Disabled.
- **Form Login / HTTP Basic:** Disabled.

## API Endpoints

| Method | Endpoint | Access | Description | Request Body | Response |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **POST** | `/api/auth/register` | Public | Register a new user | `RegisterRequest` (email, password, etc.) | `RegisterResponse<User>` |
| **POST** | `/api/auth/login` | Public | Authenticate and get token | `LoginRequest` (email, password) | `LoginResponse` (token) |
| **GET** | `/api/public/hello` | Public | Test public access | N/A | String ("Public API working") |
| **GET** | `/api/secure/hello` | Secured | Test secured access | N/A | String ("Secure API working") |

## Data Models (DTOs)
- **LoginRequest:** `email`, `password`
- **RegisterRequest:** `email`, `password`, `name`, etc.
- **User Entity:** Stores user credentials and role.
- **Role Entity:** Stores roles (e.g., USER, ADMIN).
