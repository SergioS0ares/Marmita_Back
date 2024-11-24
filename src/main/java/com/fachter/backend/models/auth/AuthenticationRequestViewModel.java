package com.fachter.backend.models.auth;

public class AuthenticationRequestViewModel {

    public String username;
    public String password;

    public AuthenticationRequestViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public AuthenticationRequestViewModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
