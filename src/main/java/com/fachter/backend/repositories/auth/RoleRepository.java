package com.fachter.backend.repositories.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fachter.backend.models.auth.UserRole;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
}
