package com.management.employee_leave.Controller;

import com.management.employee_leave.manage.AppUser;
import com.management.employee_leave.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody AppUser appUser) {

        AppUser savedUser = appUserService.registerUser(appUser);

        Map<String, Object> response = new HashMap<>();
        response.put("id", savedUser.getId());
        response.put("username", savedUser.getUsername());
        response.put("role", savedUser.getRole());
        response.put("employeeId", savedUser.getEmployeeId());

        return response;
    }
}