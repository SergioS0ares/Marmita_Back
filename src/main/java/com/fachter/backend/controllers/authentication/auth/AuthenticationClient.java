package com.fachter.backend.controllers.authentication.auth;

import com.fachter.backend.viewModels.auth.AuthenticationRequestViewModel;
import com.fachter.backend.viewModels.auth.AuthenticationResponseViewModel;

public interface AuthenticationClient {
	AuthenticationResponseViewModel authenticate(AuthenticationRequestViewModel authenticationRequestViewModel);
}
