package com.j2ns.backend.config.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.j2ns.backend.config.Role;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    private final JwtRequestFilter jwtRequestFilter; // Filtro para interceptar as requisições e validar o JWT.
    
    @Value("${backend.allowed.origins}")
    private List<String> allowedOrigins; // Lista de origens permitidas para CORS, obtida do arquivo de configurações.

    // Construtor que recebe o filtro de requisição JWT.
    public SecurityConfigurer(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // Bean que cria o AuthenticationManager, configurando a autenticação do usuário e a senha.
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http, 
            PasswordEncoder passwordEncoder, 
            UserDetailsService userDetailsService
    ) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
                .userDetailsService(userDetailsService) // Serviço que carrega os detalhes do usuário.
                .passwordEncoder(passwordEncoder); // Codificador de senha (BCrypt, no caso).
        return authManagerBuilder.build(); // Retorna o AuthenticationManager configurado.
    }

    // Bean que define o PasswordEncoder a ser utilizado (BCryptPasswordEncoder).
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Retorna o codificador de senha com o algoritmo BCrypt.
    }

    // Bean que configura as regras de segurança para as requisições HTTP.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // Habilita CORS (Cross-Origin Resource Sharing).
                .csrf(AbstractHttpConfigurer::disable) // Desabilita a proteção CSRF, já que estamos usando JWT.
                .anonymous(AbstractHttpConfigurer::disable) // Desabilita a configuração de acesso anônimo.
                .authorizeHttpRequests(authorize -> authorize // Configura as permissões de acesso às rotas.
                        .requestMatchers(
                                "/api/authenticate", // Permite acesso sem autenticação a essas rotas.
                                "/api/register"
                        ).permitAll()
                        .requestMatchers("/api/user/**").hasAuthority(Role.ADMIN.name()) // Apenas admins podem acessar rotas /api/user/**
                        .anyRequest().authenticated()) // Exige autenticação para todas as outras requisições.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define que não será usada sessão HTTP.
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro JWT antes do filtro de autenticação padrão.
                .exceptionHandling(handling -> handling // Configura o tratamento de exceções de autenticação e acesso negado.
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // Retorna 401 em caso de falha na autenticação.
                        .accessDeniedHandler(new AccessDeniedHandlerImpl())); // Retorna 403 em caso de acesso negado.
        return http.build(); // Retorna a configuração de segurança.
    }

    // Bean que configura as permissões de CORS.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(allowedOrigins); // Configura as origens permitidas para CORS.
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // Permite os métodos HTTP.
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token")); // Permite os cabeçalhos necessários para a API.
        configuration.setExposedHeaders(Collections.singletonList("x-auth-token")); // Expõe o cabeçalho x-auth-token para o cliente.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a configuração de CORS para todas as rotas.
        return source; // Retorna a configuração de CORS.
    }
}
