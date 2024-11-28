package com.j2ns.backend.services.auth;

import org.springframework.stereotype.Service;

import com.j2ns.backend.interfaces.AuthenticationService;
import com.j2ns.backend.models.auth.AuthenticationResponseModel;
import com.j2ns.backend.models.auth.UserAccountModel;
import com.j2ns.backend.models.auth.UserRoleModel;
import com.j2ns.backend.utils.JsonWebTokenUtil;

import java.util.List;
import java.util.stream.Collectors;

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
        return userDetails.getUserRoles().stream()
                .map(UserRoleModel::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
