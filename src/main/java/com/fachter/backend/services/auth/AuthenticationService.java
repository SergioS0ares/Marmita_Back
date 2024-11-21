package com.fachter.backend.services.auth;

import com.fachter.backend.entities.UserAccount;
import com.fachter.backend.viewModels.auth.AuthenticationResponseViewModel;

public interface AuthenticationService {
    AuthenticationResponseViewModel getAuthenticationResponseFromUser(UserAccount userDetails);
}
