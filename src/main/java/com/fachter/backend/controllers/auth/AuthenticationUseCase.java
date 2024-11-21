package com.fachter.backend.controllers.auth;

import com.fachter.backend.viewModels.auth.AuthenticationRequestViewModel;
import com.fachter.backend.viewModels.auth.AuthenticationResponseViewModel;

public interface AuthenticationUseCase {
    AuthenticationResponseViewModel authenticate(AuthenticationRequestViewModel authenticationRequestViewModel);
}
