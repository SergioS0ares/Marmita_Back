package com.fachter.backend.useCases.auth;

import com.fachter.backend.controllers.auth.RegisterUserUseCase;
import com.fachter.backend.entities.UserAccount;
import com.fachter.backend.exceptions.InvalidDataException;
import com.fachter.backend.exceptions.UsernameAlreadyExistsException;
import com.fachter.backend.repositories.UserRepository;
import com.fachter.backend.services.auth.AuthenticationService;
import com.fachter.backend.viewModels.auth.AuthenticationResponseViewModel;
import com.fachter.backend.viewModels.auth.RegisterUserViewModel;
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
        UserAccount user = new UserAccount()
                .setUsername(registerUserViewModel.username)
                .setPassword(
                        passwordEncoder.encode(registerUserViewModel.password)
                );
        userRepository.save(user);
        return authenticationService.getAuthenticationResponseFromUser(user);
    }
}
