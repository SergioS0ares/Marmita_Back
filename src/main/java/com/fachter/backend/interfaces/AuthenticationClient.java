package com.fachter.backend.interfaces;

import com.fachter.backend.models.auth.AuthenticationRequestViewModel;
import com.fachter.backend.models.auth.AuthenticationResponseViewModel;

public interface AuthenticationClient {
	AuthenticationResponseViewModel authenticate(AuthenticationRequestViewModel authenticationRequestViewModel);
}
