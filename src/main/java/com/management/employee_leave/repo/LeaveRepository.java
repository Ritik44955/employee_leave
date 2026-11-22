package com.management.employee_leave.repo;

import com.management.employee_leave.manage.Leave;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LeaveRepository extends MongoRepository<Leave,Integer> {

    List<Leave> findByEmployeeId(int employeeId);
}
