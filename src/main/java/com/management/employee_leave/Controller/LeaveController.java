package com.management.employee_leave.Controller;

import com.management.employee_leave.manage.AppUser;
import com.management.employee_leave.manage.Leave;
import com.management.employee_leave.repo.AppUserRepository;
import com.management.employee_leave.service.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/leave")
@RequiredArgsConstructor
public class LeaveController {
    private final LeaveService leaveService;
    private final AppUserRepository appUserRepository;

    @PostMapping("/leaves")
    public Leave save(
            @Valid @RequestBody Leave leave,
            Authentication authentication) {

        AppUser appUser = appUserRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        if ("EMPLOYEE".equals(appUser.getRole())
                && appUser.getEmployeeId() != leave.getEmployeeId()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You can only apply leave for yourself"
            );
        }

        return leaveService.addLeave(leave);
    }

    @GetMapping("/leaves")
    public List<Leave> getAll() {
        return leaveService.getAllLeaves();
    }

    @GetMapping("/employee/{employeeId}")
    public List<Leave> getLeavesByEmployeeId(
            @PathVariable int employeeId,
            Authentication authentication) {

        AppUser appUser = appUserRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        // Admin sabhi employees ki leaves dekh sakta hai
        if ("ADMIN".equals(appUser.getRole())
                || "SUPER_ADMIN".equals(appUser.getRole())) {
            return leaveService.getLeavesByEmployeeId(employeeId);
        }

        // Employee sirf apni leaves dekh sakta hai
        if (appUser.getEmployeeId() != employeeId) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You can only view your own leaves"
            );
        }

        return leaveService.getLeavesByEmployeeId(employeeId);
    }
    @GetMapping("/leaves/{id}")
    public Leave getById(@PathVariable int id) {
        return leaveService.getLeaveById(id);
    }

    @PutMapping("/leaves/{id}/approve")
    public Leave approve(@PathVariable int id) {
        return leaveService.approveLeave(id);
    }

    @PutMapping("/leaves/{id}/reject")
    public Leave reject(@PathVariable int id) {
        return leaveService.rejectLeave(id);
    }

    @PutMapping("/leaves/{id}")
    public Leave update(@PathVariable int id, @RequestBody Leave leave) {
        return leaveService.updateLeave(id, leave);
    }

    @DeleteMapping("/leaves/{id}")
    public String delete(@PathVariable int id) {
        leaveService.deleteLeave(id);
        return "Leave deleted successfully";
    }
}