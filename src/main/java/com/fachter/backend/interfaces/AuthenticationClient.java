package com.fachter.backend.interfaces;

import com.fachter.backend.models.auth.AuthenticationRequestModel;
import com.fachter.backend.models.auth.AuthenticationResponseModel;

public interface AuthenticationClient {
	AuthenticationResponseModel authenticate(AuthenticationRequestModel authenticationRequestModel);
}
