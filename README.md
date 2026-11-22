# Employee Leave Management System

A RESTful backend application built using Spring Boot and MongoDB to manage employee records and employee leave requests with role-based authentication and authorization.

## Technologies Used

- Java 23
- Spring Boot 4.1.1
- Spring Security
- Spring Data MongoDB
- MongoDB
- Maven
- Lombok
- Jakarta Bean Validation
- HTTP Basic Authentication
- BCrypt Password Encoding

## Project Structure

```text
com.management.employee_leave
│
├── config
│   ├── AdminInitializer
│   ├── PasswordConfig
│   └── SecurityConfig
│
├── Controller
│   ├── AuthController
│   ├── EmployeeController
│   └── LeaveController
│
├── exception
│   ├── GlobalExceptionHandler
│   └── ResourceNotFoundException
│
├── manage
│   ├── AppUser
│   ├── Employee
│   └── Leave
│
├── repo
│   ├── AppUserRepository
│   ├── EmployeeRepository
│   └── LeaveRepository
│
├── service
│   ├── AppUserService
│   ├── CustomUserDetailsService
│   ├── EmployeeService
│   └── LeaveService
│
└── EmployeeLeaveApplication
```

## Features

### 1. Employee Management
- Create employee records.
- Retrieve all employees.
- Retrieve employee details by ID.
- Update employee information.
- Delete employee records.
- Retrieve employee details along with leave information.

### 2. Leave Management
- Apply for leave.
- Retrieve all leave records.
- Retrieve leave details by ID.
- Retrieve leaves by employee ID.
- Approve leave requests.
- Reject leave requests.
- Update and delete leave records.

### 3. Leave Validation
- Employee existence validation.
- From-date and to-date validation.
- Prevent applying for past dates.
- Maximum leave duration of 30 days.
- Overlapping leave validation.
- Default leave status: Pending.

### 4. Spring Security
- HTTP Basic Authentication.
- BCrypt password encryption.
- Custom UserDetailsService.
- Role-based authorization.
- Employee ownership verification.
- Public employee registration.
- Admin and Super Admin access control.

### 5. Role Management

| Role | Permissions |
|---|---|
| EMPLOYEE | Apply for leave and view own leaves |
| ADMIN | Manage employees and manage leave requests |
| SUPER_ADMIN | Access admin-level operations and administrative endpoints |

### 6. Exception Handling
- Global exception handling using `@RestControllerAdvice`.
- Resource not found handling.
- Validation error handling.
- Invalid JSON handling.
- HTTP status-based error responses.

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a new employee user |

### Employee APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/employ/employees` | Get all employees |
| GET | `/employ/employees/{id}` | Get employee by ID |
| GET | `/employ/employees/{id}/details` | Get employee with leave details |
| POST | `/employ/employees` | Add employee |
| PUT | `/employ/employees/{id}` | Update employee |
| DELETE | `/employ/employees/{id}` | Delete employee |

### Leave APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/leave/leaves` | Apply for leave |
| GET | `/leave/leaves` | Get all leaves |
| GET | `/leave/leaves/{id}` | Get leave by ID |
| GET | `/leave/employee/{employeeId}` | Get leaves by employee ID |
| PUT | `/leave/leaves/{id}` | Update leave |
| PUT | `/leave/leaves/{id}/approve` | Approve leave |
| PUT | `/leave/leaves/{id}/reject` | Reject leave |
| DELETE | `/leave/leaves/{id}` | Delete leave |

## Database Configuration

The application uses MongoDB.

Configure the database connection in `application.properties`:

```properties
spring.mongodb.uri=mongodb://localhost:27017/employeedb
```

Database name: `employeedb`

## How to Run the Project

1. Install Java 23.
2. Install and start MongoDB.
3. Clone the repository.
4. Open the project in IntelliJ IDEA.
5. Configure MongoDB connection in `application.properties`.
6. Run `EmployeeLeaveApplication.java`.

The application will start on:

```text
http://localhost:8080
```

## Authentication

The application uses HTTP Basic Authentication.

Protected APIs require valid username and password credentials.

Passwords are stored using BCrypt encoding.

## API Testing

APIs can be tested using Thunder Client or Postman.

Configure Basic Auth for protected endpoints and send JSON request bodies where required.

---

**Project:** Employee Leave Management System  
**Backend:** Spring Boot REST API  
**Database:** MongoDB