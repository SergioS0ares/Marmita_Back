package com.fachter.backend.useCases.auth;

import com.fachter.backend.entities.UserAccount;
import com.fachter.backend.exceptions.InvalidDataException;
import com.fachter.backend.exceptions.UsernameAlreadyExistsException;
import com.fachter.backend.repositories.UserRepository;
import com.fachter.backend.services.auth.AuthenticationServiceImpl;
import com.fachter.backend.utils.JsonWebTokenUtil;
import com.fachter.backend.viewModels.auth.RegisterUserViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseImplTest {

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private PasswordEncoder passwordEncoderMock;
    private RegisterUserUseCaseImpl useCase;
    @Captor
    private ArgumentCaptor<UserAccount> captor;
    private JsonWebTokenUtil jsonWebTokenUtil;

    @BeforeEach
    void setUp() {
        jsonWebTokenUtil = new JsonWebTokenUtil("testing-secret");
        useCase = new RegisterUserUseCaseImpl(
                userRepositoryMock,
                passwordEncoderMock,
                new AuthenticationServiceImpl(jsonWebTokenUtil)
        );
    }

    @Test
    void givenNullUsername_thenThrowException() {
        assertThrows(InvalidDataException.class, () -> useCase.register(
                new RegisterUserViewModel().setPassword("test")
        ));
    }

    @Test
    void givenNullPassword_thenThrowException() {
        assertThrows(InvalidDataException.class, () -> useCase.register(
                new RegisterUserViewModel().setUsername("test")
        ));
    }

    @Test
    void givenUsernameAlreadyExists_thenThrowException() {
        String existingUsername = "existing";
        var existingUser = new UserAccount().setUsername(existingUsername);
        when(userRepositoryMock.findByUsername(existingUsername)).thenReturn(Optional.of(existingUser));

        assertThrows(UsernameAlreadyExistsException.class, () -> useCase.register(
                new RegisterUserViewModel().setUsername(existingUsername).setPassword("anything")));
    }

    @Test
    void givenUsernameDoesNotExist_thenCreateNewUser() throws Exception {
        String newUsername = "non-existing";
        String newPassword = "new-password";
        when(userRepositoryMock.findByUsername(newUsername)).thenReturn(Optional.empty());
        when(passwordEncoderMock.encode(newPassword)).thenReturn("encoded new password");

        var response = useCase.register(new RegisterUserViewModel().setUsername(newUsername).setPassword(newPassword));

        verify(userRepositoryMock).save(captor.capture());
        var persistedUser = captor.getValue();
        assertEquals(newUsername, persistedUser.getUsername());
        assertEquals("encoded new password", persistedUser.getPassword());
        assertNotNull(response.token);
        assertEquals(newUsername, jsonWebTokenUtil.extractUsername(response.token));
        assertEquals(LocalDateTime.now().plusDays(3).toEpochSecond(OffsetDateTime.now().getOffset()) * 1000,
                response.expiresAt, 100);
    }
}