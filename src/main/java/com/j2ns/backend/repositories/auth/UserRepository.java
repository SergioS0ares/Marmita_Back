package com.j2ns.backend.repositories.auth;


import org.springframework.data.jpa.repository.JpaRepository;

import com.j2ns.backend.models.auth.UserAccountModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccountModel, Long> {
    Optional<UserAccountModel> findByUsername(String username);
}
