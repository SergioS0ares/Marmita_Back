package com.j2ns.backend.services.auth;

import org.springframework.stereotype.Service;

import com.j2ns.backend.interfaces.AuthenticationService;
import com.j2ns.backend.interfaces.RefreshAuthenticationUseCase;
import com.j2ns.backend.models.auth.AuthenticationResponseModel;
import com.j2ns.backend.models.auth.UserAccountModel;

@Service
public class RefreshAuthenticationUseCaseImpl implements RefreshAuthenticationUseCase {
    private final AuthenticationService authenticationService;

    public RefreshAuthenticationUseCaseImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public AuthenticationResponseModel getRefreshedToken(UserAccountModel user) {
        return authenticationService.getAuthenticationResponseFromUser(user);
    }
}
