package com.j2ns.backend.models.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import java.util.UUID;

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
}
