package com.j2ns.backend.services.auth;

import org.springframework.stereotype.Service;
import com.j2ns.backend.interfaces.AuthenticationService;
import com.j2ns.backend.models.auth.AuthenticationResponseModel;
import com.j2ns.backend.models.auth.UserAccountModel;
import com.j2ns.backend.models.auth.UserRoleModel;
import com.j2ns.backend.utils.JsonWebTokenUtil;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService { 
    // Implementação do serviço de autenticação responsável por gerar respostas de autenticação e gerenciar permissões de usuários.

    private final JsonWebTokenUtil jsonWebTokenUtil; // Dependência para geração e validação de tokens JWT.

    // Construtor: injeta a classe utilitária do JWT.
    public AuthenticationServiceImpl(JsonWebTokenUtil jsonWebTokenUtil) {
        this.jsonWebTokenUtil = jsonWebTokenUtil;
    }

    /**
     * Gera uma resposta de autenticação a partir de um usuário.
     * 
     * @param userDetails Modelo contendo os detalhes do usuário.
     * @return Um modelo de resposta contendo o token JWT, permissões e data de expiração.
     */
    public AuthenticationResponseModel getAuthenticationResponseFromUser(UserAccountModel userDetails) {
        final String jwt = jsonWebTokenUtil.generateToken(userDetails); // Gera o token JWT para o usuário.
        
        // Retorna o modelo de resposta com o token e outras informações:
        return new AuthenticationResponseModel()
                .setAuthorities(getUserAuthorities(userDetails)) // Define as permissões do usuário.
                .setExpiresAt(jsonWebTokenUtil.extractExpiration(jwt).getTime()) // Define a data de expiração do token.
                .setToken(jwt); // Define o token JWT gerado.
    }

    /**
     * Extrai as permissões (authorities) do usuário.
     * 
     * @param userDetails Modelo contendo os detalhes do usuário.
     * @return Lista de permissões (roles) do usuário em ordem alfabética.
     */
    private List<String> getUserAuthorities(UserAccountModel userDetails) {
        return userDetails.getUserRoles() // Obtém a lista de roles do usuário.
                .stream() // Cria um stream para processar os dados.
                .map(UserRoleModel::getName) // Transforma cada role em seu nome (string).
                .sorted() // Ordena os nomes das roles em ordem alfabética.
                .toList(); // Converte o stream em uma lista.
    }
}
