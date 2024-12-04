package com.j2ns.backend.utils;

public class EntregaDTO {
    private int id;
    private int longitude; // Coordenada X
    private int latitude; // Coordenada Y
    private int distancia; // Distância em relação ao restaurante
    private int quantidadeMercadoria; // Quantidade de mercadorias
    
    private int tempoEstimado; // Tempo estimado para a entrega
    /* 1 a 5
    1 - 10:00 as 11:00
    2 - 11:00 as 12:00
    3 - 12:00 as 13:00
    4 - 13:00 as 14:00
    5 - 10:00 as 14:00 */

    
    // Construtor
    public EntregaDTO(int id, int x, int y, int quantidadeMercadoria) {
        this.id = id;
        this.longitude = x;
        this.latitude = y;
        this.quantidadeMercadoria = quantidadeMercadoria;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int x) {
        this.longitude = x;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int y) {
        this.latitude = y;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public int getQuantidadeMercadoria() {
        return quantidadeMercadoria;
    }

    public void setQuantidadeMercadoria(int quantidadeMercadoria) {
        this.quantidadeMercadoria = quantidadeMercadoria;
    }

    public int getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(int tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }
}
