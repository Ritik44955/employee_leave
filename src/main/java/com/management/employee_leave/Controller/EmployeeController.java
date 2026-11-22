package com.management.employee_leave.Controller;

import com.management.employee_leave.manage.Employee;
import com.management.employee_leave.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employ")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

@GetMapping( "/employees")
    public List<Employee> getAll() {
    List<Employee> employees = employeeService.getAllEmployees();
    return employees;
}

    @GetMapping("/employees/{id}")
    public Employee getById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

@PostMapping("/employees")
public Employee save(@Valid @RequestBody Employee employee){return employeeService.addEmployee(employee);
}

@PutMapping("/employees/{id}")
public Employee update(@PathVariable int id,
                       @RequestBody Employee employee) {
    return employeeService.updateEmployee(id, employee);
}

    @DeleteMapping("/employees/{id}")
    public String delete(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return "Employee deleted successfully";
    }

    @GetMapping("/employees/{id}/details")
    public Map<String, Object> getEmployeeWithLeaves(@PathVariable int id) {
        return employeeService.getEmployeeWithLeaves(id);
    }
}
