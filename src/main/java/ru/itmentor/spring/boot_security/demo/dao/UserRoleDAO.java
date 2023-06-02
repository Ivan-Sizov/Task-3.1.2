package ru.itmentor.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmentor.spring.boot_security.demo.model.UserRole;

public interface UserRoleDAO extends JpaRepository<UserRole, Long> {
    UserRole findByRole(String role);
}
