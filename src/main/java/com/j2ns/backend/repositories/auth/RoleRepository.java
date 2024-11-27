package com.fachter.backend.repositories.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fachter.backend.models.auth.UserRoleModel;

public interface RoleRepository extends JpaRepository<UserRoleModel, Long> {
}
