package com.fachter.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.j2ns.backend.models.auth.UserAccountModel;
import com.j2ns.backend.repositories.auth.UserRepository;
import com.j2ns.backend.services.auth.DefaultUserDetailsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;
    private DefaultUserDetailsService service;

    @BeforeEach
    void setUp() {
        service = new DefaultUserDetailsService(userRepository);
    }

    @Test
    void givenNoUserThenThrowException() {
        when(userRepository.findByUsername("invalid")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("invalid"));
    }

    @Test
    void givenUserThenReturn() {
        UserAccountModel existingUser = new UserAccountModel().setUsername("valid");
        when(userRepository.findByUsername("valid")).thenReturn(Optional.of(existingUser));

        var returnedUser = service.loadUserByUsername("valid");

        assertEquals(existingUser, returnedUser);
    }
}