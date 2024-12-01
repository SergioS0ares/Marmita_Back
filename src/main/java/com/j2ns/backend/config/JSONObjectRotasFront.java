package com.j2ns.backend.config;

import java.util.UUID;

public class JSONObjectRotasFront {

    private UUID id;

    private String nome;

    private String latitude;

    private String longitude;

    private int quantidadeMarmitas;

    private double distanciaViagem;

    private double tempoViagem;

    private String sujestH;
    
    private int capacidadeMarmitas;

    // Métodos getters e setters

    public int getCapacidadeMarmitas() {
		return capacidadeMarmitas;
	}
    
    public void setCapacidadeMarmitas(int capacidadeMarmitas) {
		this.capacidadeMarmitas = capacidadeMarmitas;
	}
    
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
