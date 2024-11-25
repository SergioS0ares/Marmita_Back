package com.fachter.backend.models.auth;

public class AuthenticationRequestModel {

    public String username;
    public String password;

    public AuthenticationRequestModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public AuthenticationRequestModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
