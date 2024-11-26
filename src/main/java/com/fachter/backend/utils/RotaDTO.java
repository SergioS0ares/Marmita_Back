package com.fachter.backend.utils;

import java.util.List;

public class RotaDTO {
    // Atributos
    private List<EntregaDTO> entregas; // Criando um objeto de EntregaDTO
    private int totalDistancia;
    private int totalMercadorias;

    // Construtor
    public RotaDTO(List<EntregaDTO> entregas) {
        this.entregas = entregas;
        this.totalDistancia = 0;
        this.totalMercadorias = 0;
        for (EntregaDTO entrega : entregas) {
            this.totalDistancia += entrega.getDistancia();
            this.totalMercadorias += entrega.getQuantidadeMercadoria();
        }
    }

    // Get e Set
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
