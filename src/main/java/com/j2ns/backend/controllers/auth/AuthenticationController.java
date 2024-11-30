package com.j2ns.backend.controllers.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.j2ns.backend.interfaces.AuthenticationUseCase;
import com.j2ns.backend.interfaces.RefreshAuthenticationUseCase;
import com.j2ns.backend.models.auth.AuthenticationRequestModel;
import com.j2ns.backend.models.auth.AuthenticationResponseModel;
import com.j2ns.backend.models.auth.UserAccountModel;

@RestController // Indica que esta classe é um controlador que lida com requisições HTTP.
@RequestMapping("/api") // Define o caminho base para todas as rotas desta classe. Todas as rotas terão o prefixo "/api".
public class AuthenticationController {

    private final AuthenticationUseCase authenticationUseCase; // Interface usada para autenticação do usuário.
    private final RefreshAuthenticationUseCase refreshAuthenticationUseCase; // Interface usada para refrescar o token de autenticação.

    // Construtor da classe, que recebe as interfaces de autenticação e de refresco do token.
    public AuthenticationController(AuthenticationUseCase authenticationUseCase, RefreshAuthenticationUseCase refreshAuthenticationUseCase) {
        this.authenticationUseCase = authenticationUseCase; // Inicializa a interface de autenticação.
        this.refreshAuthenticationUseCase = refreshAuthenticationUseCase; // Inicializa a interface de refresco de token.
    }

    // Endpoint para autenticar o usuário e gerar um token.
    @PostMapping("/authenticate") // A rota "/authenticate" que recebe um POST para autenticação.
    public ResponseEntity<AuthenticationResponseModel> createAuthToken(@RequestBody AuthenticationRequestModel authenticationRequestModel) {
        try {
            // Tenta autenticar o usuário com os dados recebidos na requisição.
            AuthenticationResponseModel response = authenticationUseCase.authenticate(authenticationRequestModel);
            return ResponseEntity.ok(response); // Retorna o token de autenticação gerado com sucesso (HTTP 200).
        } catch (AuthenticationException e) {
            // Em caso de falha na autenticação, retorna um erro HTTP 401 (Não Autorizado).
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // Endpoint para gerar um novo token de autenticação (refresh token) usando o usuário autenticado.
    @GetMapping("/refresh-token") // A rota "/refresh-token" que recebe um GET para refrescar o token.
    public ResponseEntity<AuthenticationResponseModel> refreshToken(@AuthenticationPrincipal UserAccountModel user) {
        // Usa o usuário autenticado para gerar um novo token de autenticação (refresh).
        return ResponseEntity.ok(refreshAuthenticationUseCase.getRefreshedToken(user));
    }
}
