package com.fachter.backend.useCases.auth;

import com.fachter.backend.controllers.auth.RefreshAuthenticationUseCase;
import com.fachter.backend.entities.UserAccount;
import com.fachter.backend.services.auth.AuthenticationService;
import com.fachter.backend.viewModels.auth.AuthenticationResponseViewModel;
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
