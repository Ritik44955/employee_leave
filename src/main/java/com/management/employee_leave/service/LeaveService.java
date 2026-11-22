package com.management.employee_leave.service;

import com.management.employee_leave.exception.ResourceNotFoundException;
import com.management.employee_leave.manage.Leave;
import com.management.employee_leave.repo.EmployeeRepository;
import com.management.employee_leave.repo.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    public List<Leave> getLeavesByEmployeeId(int employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }
    // CREATE
//    public Leave addLeave(Leave leave) {
//
//        LocalDate from = LocalDate.parse(leave.getFromDate());
//        LocalDate to = LocalDate.parse(leave.getToDate());
//
//        if (from.isAfter(to)) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "From date cannot be after to date"
//            );
//        }
//        leave.setStatus("Pending");
//        return leaveRepository.save(leave);
//    }
    public Leave addLeave(Leave leave) {

        employeeRepository.findById(leave.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + leave.getEmployeeId()
                        ));

        LocalDate from = LocalDate.parse(leave.getFromDate());
        LocalDate to = LocalDate.parse(leave.getToDate());
        if (from.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Leave cannot be applied for past dates"
            );
        }

        if (from.isAfter(to)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "From date cannot be after to date"
            );
        }

        long leaveDays = java.time.temporal.ChronoUnit.DAYS
                .between(from, to) + 1;

        if (leaveDays > 30) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Leave cannot be more than 30 days"
            );
        }


//overlapping leave validation

        List<Leave> existingLeaves =
                leaveRepository.findByEmployeeId(leave.getEmployeeId());

        for (Leave existing : existingLeaves) {

            if (!"Rejected".equalsIgnoreCase(existing.getStatus())) {

                LocalDate existingFrom =
                        LocalDate.parse(existing.getFromDate());

                LocalDate existingTo =
                        LocalDate.parse(existing.getToDate());

                if (!from.isAfter(existingTo) &&
                        !to.isBefore(existingFrom)) {

                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Leave dates overlap with an existing leave"
                    );
                }
            }
        }

        // Generate next numeric ID
        int nextId = leaveRepository.findAll()
                .stream()
                .map(Leave::getId)
                .filter(id -> id != null)
                .max(Integer::compareTo)
                .orElse(0) + 1;

        leave.setId(nextId);
        leave.setStatus("Pending");

        return leaveRepository.save(leave);
    }

    public Leave approveLeave(int id) {
        Leave leave = leaveRepository.findById(id).orElse(null);

        if (leave != null) {
            leave.setStatus("Approved");
            return leaveRepository.save(leave);
        }

        return null;
    }

    public Leave rejectLeave(int id) {
        Leave leave = leaveRepository.findById(id).orElse(null);

        if (leave != null) {
            leave.setStatus("Rejected");
            return leaveRepository.save(leave);
        }

        return null;
    }

    // READ - All
    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    // READ - By ID
    public Leave getLeaveById(int id) {
        return leaveRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave not found with id: " + id));
    }

    // UPDATE
    public Leave updateLeave(int id, @NonNull Leave leave) {
        leaveRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave not found with id: " + id));

        leave.setId(id);
        return leaveRepository.save(leave);
    }

    // DELETE
    public void deleteLeave(int id) {
        leaveRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Leave not found with id: " + id));

        leaveRepository.deleteById(id);
    }
}
