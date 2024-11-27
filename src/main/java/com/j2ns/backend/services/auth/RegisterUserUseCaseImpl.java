package com.j2ns.backend.services.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.j2ns.backend.exceptions.InvalidDataException;
import com.j2ns.backend.exceptions.UsernameAlreadyExistsException;
import com.j2ns.backend.interfaces.AuthenticationService;
import com.j2ns.backend.interfaces.RegisterUserUseCase;
import com.j2ns.backend.models.auth.AuthenticationResponseModel;
import com.j2ns.backend.models.auth.RegisterUserModel;
import com.j2ns.backend.models.auth.UserAccountModel;
import com.j2ns.backend.repositories.auth.UserRepository;

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
