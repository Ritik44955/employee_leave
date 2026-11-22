package com.management.employee_leave.config;

import com.management.employee_leave.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authenticationProvider(authenticationProvider)

                .authorizeHttpRequests(auth -> auth

                        // Registration
                        .requestMatchers("/auth/register").permitAll()

                        // Super Admin only
                        .requestMatchers("/admin/**")
                        .hasRole("SUPER_ADMIN")

                        // Employee listing and management
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/employ/employees",
                                "/employ/employees/{id}"
                        ).hasAnyRole("ADMIN", "SUPER_ADMIN")

                        .requestMatchers("/employ/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN")

                        // Leave application
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,
                                "/leave/leaves"
                        ).hasAnyRole("EMPLOYEE", "ADMIN", "SUPER_ADMIN")

                        // Leave actions restricted to admins
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/leave/leaves",
                                "/leave/leaves/{id}"
                        ).hasAnyRole("ADMIN", "SUPER_ADMIN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.PUT,
                                "/leave/leaves/**"
                        ).hasAnyRole("ADMIN", "SUPER_ADMIN")

                        .requestMatchers(
                                org.springframework.http.HttpMethod.DELETE,
                                "/leave/leaves/**"
                        ).hasAnyRole("ADMIN", "SUPER_ADMIN")

                        // Employee leave lookup
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/leave/employee/**"
                        ).hasAnyRole("EMPLOYEE", "ADMIN", "SUPER_ADMIN")

                        // Remaining requests require authentication
                        .anyRequest().authenticated()
                )

                .httpBasic(basic -> {});

        return http.build();
    }
}