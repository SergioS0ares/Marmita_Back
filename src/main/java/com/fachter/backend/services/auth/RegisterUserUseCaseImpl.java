package com.fachter.backend.services.auth;

import com.fachter.backend.exceptions.InvalidDataException;
import com.fachter.backend.exceptions.UsernameAlreadyExistsException;
import com.fachter.backend.interfaces.AuthenticationService;
import com.fachter.backend.interfaces.RegisterUserUseCase;
import com.fachter.backend.models.auth.AuthenticationResponseViewModel;
import com.fachter.backend.models.auth.RegisterUserViewModel;
import com.fachter.backend.models.auth.UserAccount;
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
    public AuthenticationResponseViewModel register(RegisterUserViewModel registerUserViewModel) throws UsernameAlreadyExistsException, InvalidDataException {
        if (registerUserViewModel.username == null || registerUserViewModel.password == null)
            throw new InvalidDataException();
        if (userRepository.findByUsername(registerUserViewModel.username).isPresent())
            throw new UsernameAlreadyExistsException();
        UserAccount user = new UserAccount().setUsername(registerUserViewModel.username)
        		.setPassword(passwordEncoder.encode(registerUserViewModel.password));
        userRepository.save(user);
        return authenticationService.getAuthenticationResponseFromUser(user);
    }
}
