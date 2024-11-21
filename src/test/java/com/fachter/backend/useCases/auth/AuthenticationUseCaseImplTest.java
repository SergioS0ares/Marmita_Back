package com.fachter.backend.useCases.auth;

import com.fachter.backend.config.Role;
import com.fachter.backend.entities.UserAccount;
import com.fachter.backend.entities.UserRole;
import com.fachter.backend.services.auth.AuthenticationServiceImpl;
import com.fachter.backend.utils.JsonWebTokenUtil;
import com.fachter.backend.viewModels.auth.AuthenticationRequestViewModel;
import com.fachter.backend.viewModels.auth.AuthenticationResponseViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationUseCaseImplTest {

    @Mock
    private UserDetailsService userDetailsServiceMock;
    @Mock
    private AuthenticationManager authenticationManagerMock;
    @Captor
    ArgumentCaptor<UsernamePasswordAuthenticationToken> captor;
    private AuthenticationUseCaseImpl useCase;
    private JsonWebTokenUtil jsonWebTokenUtil;

    @BeforeEach
    void setUp() {
        jsonWebTokenUtil = new JsonWebTokenUtil("testing-secret");
        useCase = new AuthenticationUseCaseImpl(
                userDetailsServiceMock,
                authenticationManagerMock,
                new AuthenticationServiceImpl(
                    jsonWebTokenUtil
                )
        );
    }

    @Test
    void givenNoUserException_thenPassException() {
        when(userDetailsServiceMock.loadUserByUsername("anything")).thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> useCase.authenticate(
                new AuthenticationRequestViewModel()
                        .setUsername("anything")
                        .setPassword("doesn't matter")
        ));
    }

    @Test
    void givenUserButAuthenticationManagerThrowsException_thenPassException() {
        String username = "valid-user";
        when(userDetailsServiceMock.loadUserByUsername(username)).thenReturn(new UserAccount().setUsername(username));
        when(authenticationManagerMock.authenticate(any())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> useCase.authenticate(
                new AuthenticationRequestViewModel().setUsername(username).setPassword("invalid-password")));
    }

    @Test
    void givenUser_thenCallAuthenticateAndReturnResponse() {
        Set<UserRole> userRoles = new HashSet<>();
        for (var role : Role.values()) {
            userRoles.add(new UserRole().setName(role.name()));
        }
        String username = "something";
        String password = "correctPassword";
        UserAccount user = new UserAccount()
                .setUsername(username)
                .setUserRoles(userRoles);
        when(userDetailsServiceMock.loadUserByUsername(username)).thenReturn(user);

        AuthenticationResponseViewModel response = useCase.authenticate(new AuthenticationRequestViewModel()
                .setUsername(username)
                .setPassword(password));

        assertEquals(List.of(Role.ADMIN.name(), Role.USER.name()), response.authorities);
        assertNotNull(response.token);
        assertEquals(username, jsonWebTokenUtil.extractUsername(response.token));
        assertEquals(LocalDateTime.now().plusDays(3).toEpochSecond(OffsetDateTime.now().getOffset()) * 1000,
                response.expiresAt, 100);

        verify(authenticationManagerMock).authenticate(captor.capture());
        assertEquals(username, captor.getValue().getPrincipal());
        assertEquals(password, captor.getValue().getCredentials());
    }
}