package com.fachter.backend.interfaces;

import com.fachter.backend.models.auth.AuthenticationRequestViewModel;
import com.fachter.backend.models.auth.AuthenticationResponseViewModel;

public interface AuthenticationUseCase {
    AuthenticationResponseViewModel authenticate(AuthenticationRequestViewModel authenticationRequestViewModel);
}
