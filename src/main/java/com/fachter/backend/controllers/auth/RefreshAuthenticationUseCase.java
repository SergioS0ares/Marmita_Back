package com.fachter.backend.controllers.auth;

import com.fachter.backend.entities.UserAccount;
import com.fachter.backend.viewModels.auth.AuthenticationResponseViewModel;

public interface RefreshAuthenticationUseCase {
    AuthenticationResponseViewModel getRefreshedToken(UserAccount user);
}
