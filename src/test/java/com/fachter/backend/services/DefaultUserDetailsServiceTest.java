package com.fachter.backend.services;

import com.fachter.backend.entities.UserAccount;
import com.fachter.backend.repositories.UserRepository;
import com.fachter.backend.services.auth.DefaultUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        UserAccount existingUser = new UserAccount().setUsername("valid");
        when(userRepository.findByUsername("valid")).thenReturn(Optional.of(existingUser));

        var returnedUser = service.loadUserByUsername("valid");

        assertEquals(existingUser, returnedUser);
    }
}