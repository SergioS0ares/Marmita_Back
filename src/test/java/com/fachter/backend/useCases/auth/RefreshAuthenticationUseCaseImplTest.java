package com.fachter.backend.useCases.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.j2ns.backend.config.Role;
import com.j2ns.backend.models.auth.AuthenticationResponseModel;
import com.j2ns.backend.models.auth.UserAccountModel;
import com.j2ns.backend.models.auth.UserRoleModel;
import com.j2ns.backend.services.auth.AuthenticationServiceImpl;
import com.j2ns.backend.services.auth.RefreshAuthenticationUseCaseImpl;
import com.j2ns.backend.utils.JsonWebTokenUtil;

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
        UserAccountModel user = new UserAccountModel()
                .setUsername("currentUser")
                .setUserRoles(Set.of(new UserRoleModel().setName(Role.USER.name())));

        AuthenticationResponseModel response = useCase.getRefreshedToken(user);

        assertEquals("currentUser", jwtUtil.extractUsername(response.token));
        long expected = LocalDateTime.now().plusDays(3).toEpochSecond(OffsetDateTime.now().getOffset()) * 1000;
        assertEquals(expected, response.expiresAt, 100);
    }
}