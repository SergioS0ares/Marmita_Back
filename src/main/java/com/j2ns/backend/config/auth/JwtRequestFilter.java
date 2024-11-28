package com.j2ns.backend.config.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.j2ns.backend.services.auth.DefaultUserDetailsService;
import com.j2ns.backend.utils.JsonWebTokenUtil;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter { // Filtro para interceptar as requisições HTTP e validar o JWT

    private final DefaultUserDetailsService userDetailsService; // Serviço responsável por carregar os detalhes do usuário
    private final JsonWebTokenUtil jsonWebTokenUtil; // Utilitário para gerar e validar o JWT

    // Construtor: injeta as dependências do serviço de detalhes do usuário e do utilitário de JWT
    public JwtRequestFilter(DefaultUserDetailsService userDetailsService, JsonWebTokenUtil jsonWebTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jsonWebTokenUtil = jsonWebTokenUtil;
    }

    // Método do filtro que será chamado para cada requisição.
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        setAuthenticationContextFromJwt(request); // Define o contexto de autenticação usando o JWT
        chain.doFilter(request, response); // Passa a requisição adiante para o próximo filtro ou handler
    }

    // Método que extrai o token JWT do cabeçalho de autorização e define o contexto de autenticação
    private void setAuthenticationContextFromJwt(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization"); // Extrai o cabeçalho de autorização

        // Verifica se o cabeçalho contém o prefixo "Bearer " e valida a presença do token
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            return;

        // Extrai o token JWT do cabeçalho, removendo o prefixo "Bearer "
        String jwt = authorizationHeader.substring(7);
        String username = jsonWebTokenUtil.extractUsername(jwt); // Extrai o nome de usuário do token

        if (username == null)
            return; // Se não houver nome de usuário no token, não faz nada

        // Carrega os detalhes do usuário a partir do nome de usuário extraído do token
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        
        // Se o token não for válido, não realiza nenhuma ação
        if (!jsonWebTokenUtil.validateToken(jwt, userDetails))
            return;

        // Cria um token de autenticação baseado nos detalhes do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()); // Não passamos senha, apenas as authorities (autoridades)

        // Define os detalhes da autenticação para a requisição (ex: IP, etc.)
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Define o token de autenticação no contexto de segurança da aplicação
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
