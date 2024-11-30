package com.j2ns.backend.models.auth;

public class AuthenticationRequestModel {

    public String username; // Campo para armazenar o nome de usuário fornecido na requisição.
    public String password; // Campo para armazenar a senha fornecida na requisição.

    // Método para definir o nome de usuário e retornar o próprio objeto para permitir o encadeamento de métodos (fluent interface).
    public AuthenticationRequestModel setUsername(String username) {
        this.username = username; // Atribui o nome de usuário ao campo.
        return this; // Retorna o próprio objeto para possibilitar o encadeamento de chamadas.
    }

    // Método para definir a senha e retornar o próprio objeto para permitir o encadeamento de métodos (fluent interface).
    public AuthenticationRequestModel setPassword(String password) {
        this.password = password; // Atribui a senha ao campo.
        return this; // Retorna o próprio objeto para possibilitar o encadeamento de chamadas.
    }
}
