package com.management.employee_leave.config;

import com.management.employee_leave.manage.AppUser;
import com.management.employee_leave.repo.AppUserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner createSuperAdmin(
            AppUserRepository repository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.username:}") String username,
            @Value("${app.admin.password:}") String password) {

        return args -> {

            if (username.isBlank() || password.isBlank()) {
                return;
            }

            if (repository.findByUsername(username).isEmpty()) {
                AppUser admin = new AppUser();
                admin.setUsername(username);
                admin.setPassword(passwordEncoder.encode(password));
                admin.setRole("SUPER_ADMIN");

                repository.save(admin);
            }
        };
    }
}