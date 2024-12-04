package com.j2ns.backend.models.auth;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationResponseModel {

    public String token; // Token JWT retornado após a autenticação.
    public long expiresAt; // Data e hora em que o token vai expirar (em formato timestamp).
    public List<String> authorities = new ArrayList<>(); // Lista de permissões ou roles atribuídas ao usuário autenticado.

    // Método que define o token e retorna a instância atual do objeto para encadeamento de chamadas.
    public AuthenticationResponseModel setToken(String token) {
        this.token = token; // Atribui o valor do token.
        return this; // Retorna a instância atual do objeto para permitir encadeamento de métodos.
    }

    // Método que define a data de expiração do token e retorna a instância atual do objeto para encadeamento.
    public AuthenticationResponseModel setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt; // Atribui o valor de expiração.
        return this; // Retorna a instância atual do objeto.
    }

    // Método que define as permissões (roles) e retorna a instância atual do objeto para encadeamento.
    public AuthenticationResponseModel setAuthorities(List<String> authorities) {
        this.authorities = authorities; // Atribui a lista de permissões.
        return this; // Retorna a instância atual do objeto.
    }
}
