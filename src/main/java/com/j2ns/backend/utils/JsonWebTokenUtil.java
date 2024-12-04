package com.j2ns.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JsonWebTokenUtil { // Classe responsável pela geração e validação de tokens JWT.

    private static final long JWT_TOKEN_VALIDITY = 60 * 60 * 24 * 3; // Define a validade do token para 3 dias (em segundos).
    
    private final String secretKey; // Chave secreta usada para assinar e validar os tokens.

    // Construtor: inicializa a chave secreta com o valor definido nas propriedades do sistema.
    public JsonWebTokenUtil(@Value("${backend.jwtSecretKey}") String secretKey) {
        this.secretKey = secretKey;
    }

    // Extrai o nome de usuário (subject) do token JWT.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrai a data de expiração do token JWT.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extrai uma informação (claim) específica do token usando uma função fornecida como argumento.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Obtém todos os claims do token.
        return claimsResolver.apply(claims); // Aplica a função passada para obter o claim desejado.
    }

    // Método auxiliar que extrai todos os claims do token.
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey) // Define a chave secreta para decodificar o token.
                .parseClaimsJws(token) // Decodifica o token JWT.
                .getBody(); // Retorna o corpo do token (os claims).
    }

    // Verifica se o token já expirou.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Compara a data de expiração com a data atual.
    }

    // Gera um token JWT com informações básicas (apenas o nome de usuário como subject).
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(); // Nenhum claim adicional é adicionado.
        return createToken(claims, userDetails.getUsername());
    }

    // Gera um token JWT com claims adicionais.
    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        return createToken(claims, userDetails.getUsername());
    }

    // Método privado que cria o token com base nos claims e no nome de usuário.
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Adiciona os claims (dados adicionais, se houver).
                .setSubject(subject) // Define o nome de usuário como subject.
                .setIssuedAt(new Date(System.currentTimeMillis())) // Define a data de emissão do token.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * JWT_TOKEN_VALIDITY)) // Define a validade do token.
                .signWith(SignatureAlgorithm.HS256, secretKey) // Assina o token usando o algoritmo HS256 e a chave secreta.
                .compact(); // Compacta e retorna o token JWT como uma string.
    }

    // Valida o token, verificando se o nome de usuário bate e se o token não expirou.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Extrai o nome de usuário do token.
        return (username.equals(userDetails.getUsername())) // Verifica se o nome de usuário é o mesmo.
                && !isTokenExpired(token); // Verifica se o token não expirou.
    }

    // Obtém um claim específico do token com base em uma chave.
    public Object getClaimByKey(String token, String key) {
        return extractAllClaims(token).get(key); // Retorna o valor do claim associado à chave fornecida.
    }
}
