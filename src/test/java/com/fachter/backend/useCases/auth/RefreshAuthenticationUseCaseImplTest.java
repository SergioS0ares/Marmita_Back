package com.fachter.backend.useCases.auth;

import com.fachter.backend.config.Role;
import com.fachter.backend.models.auth.AuthenticationResponseViewModel;
import com.fachter.backend.models.auth.UserAccount;
import com.fachter.backend.models.auth.UserRole;
import com.fachter.backend.services.auth.AuthenticationServiceImpl;
import com.fachter.backend.services.auth.RefreshAuthenticationUseCaseImpl;
import com.fachter.backend.utils.JsonWebTokenUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RefreshAuthenticationUseCaseImplTest {

    @Test
    void givenUserThenReturnNewToken() {
        JsonWebTokenUtil jwtUtil = new JsonWebTokenUtil("cool-testing-secret");
        var useCase = new RefreshAuthenticationUseCaseImpl(
                new AuthenticationServiceImpl(
                        jwtUtil
                )
        );
        UserAccount user = new UserAccount()
                .setUsername("currentUser")
                .setUserRoles(Set.of(new UserRole().setName(Role.USER.name())));

        AuthenticationResponseViewModel response = useCase.getRefreshedToken(user);

        assertEquals("currentUser", jwtUtil.extractUsername(response.token));
        long expected = LocalDateTime.now().plusDays(3).toEpochSecond(OffsetDateTime.now().getOffset()) * 1000;
        assertEquals(expected, response.expiresAt, 100);
    }
}