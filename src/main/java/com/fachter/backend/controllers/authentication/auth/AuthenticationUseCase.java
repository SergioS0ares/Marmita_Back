package com.fachter.backend.controllers.authentication.auth;

import com.fachter.backend.viewModels.auth.AuthenticationRequestViewModel;
import com.fachter.backend.viewModels.auth.AuthenticationResponseViewModel;

public interface AuthenticationUseCase {
    AuthenticationResponseViewModel authenticate(AuthenticationRequestViewModel authenticationRequestViewModel);
}
