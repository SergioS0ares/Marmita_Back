package com.j2ns.backend.interfaces;

import com.j2ns.backend.models.auth.AuthenticationResponseModel;
import com.j2ns.backend.models.auth.UserAccountModel;

public interface RefreshAuthenticationUseCase {
    AuthenticationResponseModel getRefreshedToken(UserAccountModel user);
}
