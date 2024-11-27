package com.fachter.backend.services.auth;

import com.fachter.backend.interfaces.AuthenticationService;
import com.fachter.backend.models.auth.AuthenticationResponseModel;
import com.fachter.backend.models.auth.UserAccountModel;
import com.fachter.backend.models.auth.UserRoleModel;
import com.fachter.backend.utils.JsonWebTokenUtil;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JsonWebTokenUtil jsonWebTokenUtil;

    public AuthenticationServiceImpl(JsonWebTokenUtil jsonWebTokenUtil) {
        this.jsonWebTokenUtil = jsonWebTokenUtil;
    }

    public AuthenticationResponseModel getAuthenticationResponseFromUser(UserAccountModel userDetails) {
        final String jwt = jsonWebTokenUtil.generateToken(userDetails);
        return new AuthenticationResponseModel()
                .setAuthorities(getUserAuthorities(userDetails))
                .setExpiresAt(jsonWebTokenUtil.extractExpiration(jwt).getTime())
                .setToken(jwt);
    }

    private List<String> getUserAuthorities(UserAccountModel userDetails) {
        return userDetails.getUserRoles().stream().map(UserRoleModel::getName).sorted().toList();
    }
}
