package com.fachter.backend.repositories.auth;


import org.springframework.data.jpa.repository.JpaRepository;

import com.fachter.backend.models.auth.UserAccount;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUsername(String username);
}
