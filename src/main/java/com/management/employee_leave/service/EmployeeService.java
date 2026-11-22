package com.management.employee_leave.service;

import com.management.employee_leave.exception.ResourceNotFoundException;
import com.management.employee_leave.manage.Employee;
import com.management.employee_leave.manage.Leave;
import com.management.employee_leave.repo.EmployeeRepository;
import com.management.employee_leave.repo.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final LeaveRepository leaveRepository;


    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();}



    // READ - By ID
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id: " + id));
    }

    // UPDATE
    public Employee updateEmployee(int id, @NonNull Employee employee) {
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    // DELETE
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    public Map<String, Object> getEmployeeWithLeaves(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id: " + employeeId));



        List<Leave> leaves = leaveRepository.findByEmployeeId(employeeId);

        Map<String, Object> result = new HashMap<>();
        result.put("employee", employee);
        result.put("leaves", leaves);

        return result;
    }

}
