package com.fachter.backend.utils;

public class EntregaDTO {
    // Atributos
    private int id;
    private int horarioPrioridade;
         /* 1 a 5
            1 - 10:00 as 11:00
            2 - 11:00 as 12:00
            3 - 12:00 as 13:00
            4 - 13:00 as 14:00
            5 - 10:00 as 14:00 */

    private int distancia;          // Distância em km
    private int quantidadeMercadoria; // O max vai ser 12

    // Construtor
    public EntregaDTO(int id, int horarioPrioridade, int distancia, int quantidadeMercadoria) {
        this.id = id;
        this.horarioPrioridade = horarioPrioridade;
        this.distancia = distancia;
        this.quantidadeMercadoria = quantidadeMercadoria;
    }

    // Get e Set
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHorarioPrioridade() {
        return horarioPrioridade;
    }

    public void setHorarioPrioridade(int horarioPrioridade) {
        this.horarioPrioridade = horarioPrioridade;
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
}

