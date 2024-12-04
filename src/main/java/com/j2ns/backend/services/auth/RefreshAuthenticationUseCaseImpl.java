package com.j2ns.backend.services.auth;

import org.springframework.stereotype.Service;

import com.j2ns.backend.interfaces.AuthenticationService; // Interface para serviços de autenticação.
import com.j2ns.backend.interfaces.RefreshAuthenticationUseCase; // Interface que define o caso de uso para renovar autenticação.
import com.j2ns.backend.models.auth.AuthenticationResponseModel; // Modelo de resposta contendo informações de autenticação.
import com.j2ns.backend.models.auth.UserAccountModel; // Modelo representando a conta do usuário.

@Service
public class RefreshAuthenticationUseCaseImpl implements RefreshAuthenticationUseCase {
    // Serviço responsável por renovar tokens de autenticação.
    
    private final AuthenticationService authenticationService; 
    // Dependência de um serviço de autenticação para lidar com tokens.

    // Construtor que injeta o serviço de autenticação.
    public RefreshAuthenticationUseCaseImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public AuthenticationResponseModel getRefreshedToken(UserAccountModel user) {
        // Método que renova o token de autenticação para um usuário específico.
        // Recebe como parâmetro o modelo da conta do usuário.
        
        return authenticationService.getAuthenticationResponseFromUser(user);
        // Chama o serviço de autenticação para gerar uma nova resposta de autenticação com base no usuário.
    }
}
