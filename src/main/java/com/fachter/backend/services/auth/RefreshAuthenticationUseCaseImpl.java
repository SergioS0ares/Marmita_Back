package com.fachter.backend.services.auth;

import com.fachter.backend.interfaces.AuthenticationService;
import com.fachter.backend.interfaces.RefreshAuthenticationUseCase;
import com.fachter.backend.models.auth.AuthenticationResponseViewModel;
import com.fachter.backend.models.auth.UserAccount;

import org.springframework.stereotype.Service;

@Service
public class RefreshAuthenticationUseCaseImpl implements RefreshAuthenticationUseCase {
    private final AuthenticationService authenticationService;

    public RefreshAuthenticationUseCaseImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public AuthenticationResponseViewModel getRefreshedToken(UserAccount user) {
        return authenticationService.getAuthenticationResponseFromUser(user);
    }
}
