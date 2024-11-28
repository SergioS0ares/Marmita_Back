package com.fachter.backend.utils;

import java.util.List;

public class RotaDTO {
    // Atributos
    private List<EntregaDTO> entregas;
    private int totalDistancia;
    private int totalMercadorias;

    // Construtor
    public RotaDTO(List<EntregaDTO> entregas) {//Recebendo valores de distancia
        this.entregas = entregas;
        this.totalDistancia = entregas.stream().mapToInt(EntregaDTO::getDistancia).sum();
        this.totalMercadorias = entregas.stream().mapToInt(EntregaDTO::getQuantidadeMercadoria).sum();
    }

    // Getters e Setters
    public List<EntregaDTO> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<EntregaDTO> entregas) {
        this.entregas = entregas;
    }

    public int getTotalDistancia() {
        return totalDistancia;
    }

    public void setTotalDistancia(int totalDistancia) {
        this.totalDistancia = totalDistancia;
    }

    public int getTotalMercadorias() {
        return totalMercadorias;
    }

    public void setTotalMercadorias(int totalMercadorias) {
        this.totalMercadorias = totalMercadorias;
    }
}


    // Construtor Caso seja necessario alterar pra dinamico apenas colocar essa parte no lugar do antigo construtor

//    public RotaDTO(List<EntregaDTO> entregas) {
//        this.entregas = entregas;
//        this.totalDistancia = 0;
//        this.totalMercadorias = 0;
//        for (EntregaDTO entrega : entregas) {
//            this.totalDistancia += entrega.getDistancia();
//            this.totalMercadorias += entrega.getQuantidadeMercadoria();
//        }
//    }
