package com.management.employee_leave.repo;


import com.management.employee_leave.manage.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository <Employee, Integer>  {
}
