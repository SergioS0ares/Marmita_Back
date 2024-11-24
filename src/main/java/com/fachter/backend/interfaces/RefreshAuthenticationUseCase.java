package com.fachter.backend.interfaces;

import com.fachter.backend.models.auth.AuthenticationResponseViewModel;
import com.fachter.backend.models.auth.UserAccount;

public interface RefreshAuthenticationUseCase {
    AuthenticationResponseViewModel getRefreshedToken(UserAccount user);
}
