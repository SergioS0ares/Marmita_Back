package com.fachter.backend.services.auth;

import com.fachter.backend.entities.UserAccount;
import com.fachter.backend.entities.UserRole;
import com.fachter.backend.utils.JsonWebTokenUtil;
import com.fachter.backend.viewModels.auth.AuthenticationResponseViewModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JsonWebTokenUtil jsonWebTokenUtil;

    public AuthenticationServiceImpl(JsonWebTokenUtil jsonWebTokenUtil) {
        this.jsonWebTokenUtil = jsonWebTokenUtil;
    }

    public AuthenticationResponseViewModel getAuthenticationResponseFromUser(UserAccount userDetails) {
        final String jwt = jsonWebTokenUtil.generateToken(userDetails);
        return new AuthenticationResponseViewModel()
                .setAuthorities(getUserAuthorities(userDetails))
                .setExpiresAt(jsonWebTokenUtil.extractExpiration(jwt).getTime())
                .setToken(jwt);
    }

    private List<String> getUserAuthorities(UserAccount userDetails) {
        return userDetails.getUserRoles().stream().map(UserRole::getName).sorted().toList();
    }
}
