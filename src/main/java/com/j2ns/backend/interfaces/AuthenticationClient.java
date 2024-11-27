package com.j2ns.backend.interfaces;

import com.j2ns.backend.models.auth.AuthenticationRequestModel;
import com.j2ns.backend.models.auth.AuthenticationResponseModel;

public interface AuthenticationClient {
	AuthenticationResponseModel authenticate(AuthenticationRequestModel authenticationRequestModel);
}
