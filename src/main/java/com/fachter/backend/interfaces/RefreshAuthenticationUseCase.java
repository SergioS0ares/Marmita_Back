package com.fachter.backend.interfaces;

import com.fachter.backend.models.auth.AuthenticationResponseModel;
import com.fachter.backend.models.auth.UserAccountModel;

public interface RefreshAuthenticationUseCase {
    AuthenticationResponseModel getRefreshedToken(UserAccountModel user);
}
