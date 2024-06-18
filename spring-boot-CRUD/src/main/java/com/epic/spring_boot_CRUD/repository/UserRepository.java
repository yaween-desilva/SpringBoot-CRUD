package com.epic.spring_boot_CRUD.repository;

import com.epic.spring_boot_CRUD.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
