package com.fachter.backend.viewModels.auth;

public class RegisterUserViewModel {
    public String username;
    public String password;

    public RegisterUserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public RegisterUserViewModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
