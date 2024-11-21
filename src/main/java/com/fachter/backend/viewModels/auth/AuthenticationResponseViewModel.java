package com.fachter.backend.viewModels.auth;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationResponseViewModel {

    public String token;
    public long expiresAt;
    public List<String> authorities = new ArrayList<>();

    public AuthenticationResponseViewModel setToken(String token) {
        this.token = token;
        return this;
    }

    public AuthenticationResponseViewModel setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public AuthenticationResponseViewModel setAuthorities(List<String> authorities) {
        this.authorities = authorities;
        return this;
    }
}
