package com.j2ns.backend.models.auth;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RestauranteModel { // Modelo de dados que representa um restaurante no sistema.

    @Id // Indica que esse campo é a chave primária da entidade.
    @GeneratedValue(strategy = GenerationType.AUTO) // Define a estratégia de geração automática do ID.
    private UUID id; // Identificador único do restaurante, gerado automaticamente como UUID.
    
    @Column(nullable = false) // Indica que essa coluna no banco de dados não pode ser nula.
    private String latitudeRestaurante; // Latitude do restaurante, necessária para o funcionamento do sistema de geolocalização.
    
    @Column(nullable = false) // Indica que essa coluna no banco de dados não pode ser nula.
    private String longitudeRestaurante; // Longitude do restaurante, necessária para o funcionamento do sistema de geolocalização.

    // Método getter para o campo `id`.
    public UUID getId() {
        return id; // Retorna o ID do restaurante.
    }
    
    // Método getter para o campo `latitudeRestaurante`.
    public String getLatitudeRestaurante() {
        return latitudeRestaurante; // Retorna a latitude do restaurante.
    }
    
    // Método getter para o campo `longitudeRestaurante`.
    public String getLongitudeRestaurante() {
        return longitudeRestaurante; // Retorna a longitude do restaurante.
    }
    
    // Método setter para o campo `id`.
    public void setId(UUID id) {
        this.id = id; // Define o ID do restaurante.
    }
    
    // Método setter para o campo `latitudeRestaurante`.
    public void setLatitudeRestaurante(String latitudeRestaurante) {
        this.latitudeRestaurante = latitudeRestaurante; // Define a latitude do restaurante.
    }
    
    // Método setter para o campo `longitudeRestaurante`.
    public void setLongitudeRestaurante(String longitudeRestaurante) {
        this.longitudeRestaurante = longitudeRestaurante; // Define a longitude do restaurante.
    }
}
