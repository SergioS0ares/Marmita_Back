package com.fachter.backend.services.auth;

import com.fachter.backend.exceptions.InvalidDataException;
import com.fachter.backend.exceptions.UsernameAlreadyExistsException;
import com.fachter.backend.interfaces.AuthenticationService;
import com.fachter.backend.interfaces.RegisterUserUseCase;
import com.fachter.backend.models.auth.AuthenticationResponseModel;
import com.fachter.backend.models.auth.RegisterUserModel;
import com.fachter.backend.models.auth.UserAccountModel;
import com.fachter.backend.repositories.auth.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    public RegisterUserUseCaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    @Override
    public AuthenticationResponseModel register(RegisterUserModel registerUserModel) throws UsernameAlreadyExistsException, InvalidDataException {
        if (registerUserModel.username == null || registerUserModel.password == null)
            throw new InvalidDataException();
        if (userRepository.findByUsername(registerUserModel.username).isPresent())
            throw new UsernameAlreadyExistsException();
        UserAccountModel user = new UserAccountModel().setUsername(registerUserModel.username)
        		.setPassword(passwordEncoder.encode(registerUserModel.password));
        userRepository.save(user);
        return authenticationService.getAuthenticationResponseFromUser(user);
    }
}
