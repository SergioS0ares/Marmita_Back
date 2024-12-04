package com.j2ns.backend.models.auth;

public class RegisterUserModel {

    public String username; // Atributo que armazena o nome de usuário do novo usuário.
    public String password; // Atributo que armazena a senha do novo usuário.

    // Método para definir o nome de usuário. Retorna o próprio objeto para encadear chamadas (fluência).
    public RegisterUserModel setUsername(String username) {
        this.username = username; // Atribui o nome de usuário recebido como parâmetro ao atributo da classe.
        return this; // Retorna a instância atual do objeto, permitindo chamadas encadeadas.
    }

    // Método para definir a senha. Retorna o próprio objeto para encadear chamadas (fluência).
    public RegisterUserModel setPassword(String password) {
        this.password = password; // Atribui a senha recebida como parâmetro ao atributo da classe.
        return this; // Retorna a instância atual do objeto, permitindo chamadas encadeadas.
    }
}
