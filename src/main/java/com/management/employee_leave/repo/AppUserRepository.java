package com.management.employee_leave.repo;

import com.management.employee_leave.manage.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppUserRepository
        extends MongoRepository<AppUser, String> {

    Optional<AppUser> findByUsername(String username);
}


