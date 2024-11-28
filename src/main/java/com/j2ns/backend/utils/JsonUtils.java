package com.j2ns.backend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils { // Classe utilitária para trabalhar com conversões de objetos para JSON.

    private static final ObjectMapper objectMapper = new ObjectMapper(); 
    // Instância do ObjectMapper da biblioteca Jackson, usada para manipular e converter objetos para JSON.

    // Método estático que converte um objeto Java em uma string JSON formatada.
    public static String convertToJson(Object object) {
        try {
            // Usa o ObjectMapper para converter o objeto em uma string JSON bonita (pretty-printed).
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            // Se ocorrer algum erro durante a conversão, uma RuntimeException é lançada com a mensagem de erro.
            throw new RuntimeException("Erro ao converter para JSON", e);
        }
    }
}
