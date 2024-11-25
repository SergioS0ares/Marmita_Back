package com.fachter.backend.models.auth;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationResponseModel {

    public String token;
    public long expiresAt;
    public List<String> authorities = new ArrayList<>();

    public AuthenticationResponseModel setToken(String token) {
        this.token = token;
        return this;
    }

    public AuthenticationResponseModel setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public AuthenticationResponseModel setAuthorities(List<String> authorities) {
        this.authorities = authorities;
        return this;
    }
}
