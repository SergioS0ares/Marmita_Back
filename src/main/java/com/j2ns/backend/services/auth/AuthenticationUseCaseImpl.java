package com.j2ns.backend.services.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.j2ns.backend.interfaces.AuthenticationService;
import com.j2ns.backend.interfaces.AuthenticationUseCase;
import com.j2ns.backend.models.auth.AuthenticationRequestModel;
import com.j2ns.backend.models.auth.AuthenticationResponseModel;
import com.j2ns.backend.models.auth.UserAccountModel;

@Service
public class AuthenticationUseCaseImpl implements AuthenticationUseCase {

    // Dependências necessárias para autenticação.
    private final UserDetailsService userDetailsService; // Serviço para carregar os detalhes do usuário.
    private final AuthenticationManager authenticationManager; // Gerenciador de autenticação do Spring Security.
    private final AuthenticationService authenticationService; // Serviço customizado para manipular a resposta de autenticação.

    // Construtor que inicializa as dependências via injeção de dependência.
    public AuthenticationUseCaseImpl(UserDetailsService userDetailsService, 
                                      AuthenticationManager authenticationManager, 
                                      AuthenticationService authenticationService) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @Override
    public AuthenticationResponseModel authenticate(AuthenticationRequestModel authenticationRequestModel) {
        // Carrega os detalhes do usuário com base no nome de usuário recebido na requisição.
        final UserAccountModel userDetails = 
            (UserAccountModel) userDetailsService.loadUserByUsername(authenticationRequestModel.username);
        
        // Realiza a autenticação com as credenciais fornecidas.
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequestModel.username, // Nome de usuário recebido na requisição.
                authenticationRequestModel.password  // Senha recebida na requisição.
        ));
        
        // Gera e retorna uma resposta de autenticação usando o serviço customizado.
        return authenticationService.getAuthenticationResponseFromUser(userDetails);
    }
}
