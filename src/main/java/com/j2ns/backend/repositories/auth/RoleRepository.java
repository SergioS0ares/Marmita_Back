package com.j2ns.backend.repositories.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.j2ns.backend.models.auth.UserRoleModel;

public interface RoleRepository extends JpaRepository<UserRoleModel, Long> {
}
