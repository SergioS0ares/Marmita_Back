package com.j2ns.backend.models.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import java.util.UUID;
import java.util.Objects;

@Entity
public class RotasModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column
    private String nome;

    @Column
    private String latitude;

    @Column
    private String longitude;

    @Column
    private int quantidadeMarmitas;

    @Column
    private double distanciaViagem;

    @Column
    private double tempoViagem;

    @Column
    private String sujestH;
    
    // toString

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RotasModel that = (RotasModel) obj;
        return nome.equals(that.nome) &&
               latitude.equals(that.latitude) &&
               longitude.equals(that.longitude); // ou qualquer outro critério único
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, latitude, longitude); // ou qualquer outro critério único
    }



    // Métodos getters e setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getQuantidadeMarmitas() {
        return quantidadeMarmitas;
    }

    public void setQuantidadeMarmitas(int quantidadeMarmitas) {
        this.quantidadeMarmitas = quantidadeMarmitas;
    }

    public double getDistanciaViagem() {
        return distanciaViagem;
    }

    public void setDistanciaViagem(double distanciaViagem) {
        this.distanciaViagem = distanciaViagem;
    }

    public double getTempoViagem() {
        return tempoViagem;
    }

    public void setTempoViagem(double tempoViagem) {
        this.tempoViagem = tempoViagem;
    }

    public String getSujestH() {
        return sujestH;
    }

    public void setSujestH(String sujestH) {
        this.sujestH = sujestH;
    }

    public void setId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("A ID não pode ser nula ou vazia.");
        }

        // Verificar se a string é um UUID válido
        try {
            UUID stringId = UUID.fromString(id); // Tenta criar um UUID com a string fornecida
            this.id = stringId;       // Se for válido, define a ID
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A ID fornecida não é válida. Deve estar no formato de um UUID.");
        }
    }

}
