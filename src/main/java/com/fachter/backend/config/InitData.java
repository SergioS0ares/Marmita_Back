package com.fachter.backend.config;

import com.fachter.backend.entities.UserAccount;
import com.fachter.backend.entities.UserRole;
import com.fachter.backend.repositories.RoleRepository;
import com.fachter.backend.repositories.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Configuration
public class InitData {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public InitData(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertInitData() {
        var roles = new HashSet<>(roleRepository.findAll());
        if (roles.isEmpty()) {
            List<UserRole> allRoles = new ArrayList<>();
            allRoles.add(new UserRole().setName(Role.ADMIN.name()));
            allRoles.add(new UserRole().setName(Role.USER.name()));
            roleRepository.saveAll(allRoles);
            roles = new HashSet<>(allRoles);
        }
        if (userRepository.findByUsername("admin").isEmpty()) {
            UserAccount admin = new UserAccount()
                    .setUsername("admin")
                    .setUserRoles(roles)
                    .setPassword(passwordEncoder.encode("admin123"));
            userRepository.save(admin);
        }
    }
}
