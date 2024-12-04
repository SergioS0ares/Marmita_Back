package com.j2ns.backend.cors.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration { // Classe de configuração do CORS (Cross-Origin Resource Sharing).

    // Definindo as constantes para os métodos HTTP que serão permitidos.
    private static final String GET = "GET"; // Método GET para requisições de leitura.
    private static final String POST = "POST"; // Método POST para requisições de envio de dados.
    private static final String PUT = "PUT"; // Método PUT para atualizações de dados.
    private static final String DELETE = "DELETE"; // Método DELETE para remoção de dados.

    // Método Bean que retorna uma instância de WebMvcConfigurer para configuração do CORS.
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        // Retorna uma implementação anônima de WebMvcConfigurer que irá configurar o CORS.
        return new WebMvcConfigurer() {
            // Sobrescreve o método addCorsMappings para configurar as permissões do CORS.
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Adiciona um mapeamento para todas as URLs ("/**") para permitir requisições CORS.
                registry.addMapping("/**")
                        // Permite os métodos HTTP especificados.
                        .allowedMethods(GET, POST, PUT, DELETE)
                        // Permite todos os cabeçalhos de requisição.
                        .allowedHeaders("*")
                        // Permite todas as origens para fazer requisições.
                        .allowedOriginPatterns("*")
                        // Permite o envio de credenciais como cookies, cabeçalhos de autenticação, etc.
                        .allowCredentials(true);
            }
        };
    }
}
