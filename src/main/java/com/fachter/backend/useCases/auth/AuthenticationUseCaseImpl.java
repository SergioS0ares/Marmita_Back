package com.fachter.backend.useCases.auth;

import com.fachter.backend.controllers.auth.AuthenticationUseCase;
import com.fachter.backend.entities.UserAccount;
import com.fachter.backend.services.auth.AuthenticationService;
import com.fachter.backend.viewModels.auth.AuthenticationRequestViewModel;
import com.fachter.backend.viewModels.auth.AuthenticationResponseViewModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUseCaseImpl implements AuthenticationUseCase {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    public AuthenticationUseCaseImpl(UserDetailsService userDetailsService, AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @Override
    public AuthenticationResponseViewModel authenticate(AuthenticationRequestViewModel authenticationRequestViewModel) {
        final UserAccount userDetails = (UserAccount) userDetailsService.loadUserByUsername(authenticationRequestViewModel.username);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequestViewModel.username,
                authenticationRequestViewModel.password
        ));
        return authenticationService.getAuthenticationResponseFromUser(userDetails);
    }
}
