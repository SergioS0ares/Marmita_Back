package com.fachter.backend.models.auth;

public class RegisterUserModel {
    public String username;
    public String password;

    public RegisterUserModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public RegisterUserModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
