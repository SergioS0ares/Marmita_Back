package com.fachter.backend.services.auth;

import com.fachter.backend.interfaces.AuthenticationService;
import com.fachter.backend.interfaces.AuthenticationUseCase;
import com.fachter.backend.models.auth.AuthenticationRequestModel;
import com.fachter.backend.models.auth.AuthenticationResponseModel;
import com.fachter.backend.models.auth.UserAccountModel;

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
    public AuthenticationResponseModel authenticate(AuthenticationRequestModel authenticationRequestModel) {
        final UserAccountModel userDetails = (UserAccountModel) userDetailsService.loadUserByUsername(authenticationRequestModel.username);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequestModel.username,
                authenticationRequestModel.password
        ));
        return authenticationService.getAuthenticationResponseFromUser(userDetails);
    }
}
