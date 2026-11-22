package com.management.employee_leave.service;

import com.management.employee_leave.manage.AppUser;
import com.management.employee_leave.repo.AppUserRepository;
import com.management.employee_leave.repo.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUser registerUser(@NonNull AppUser appUser) {

        if (appUserRepository.findByUsername(appUser.getUsername())
                .isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        employeeRepository.findById(appUser.getEmployeeId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found with id: "
                                        + appUser.getEmployeeId()
                        ));

        appUser.setRole("EMPLOYEE");

        appUser.setPassword(
                passwordEncoder.encode(appUser.getPassword())
        );

        return appUserRepository.save(appUser);
    }
}