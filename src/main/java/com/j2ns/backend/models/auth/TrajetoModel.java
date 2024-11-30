package com.j2ns.backend.models.auth;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class TrajetoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RotasModel> rotas;

    private int totalMarmitasEntregues;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RotasModel> getRotas() {
        return rotas;
    }

    public void setRotas(List<RotasModel> rotas) {
        this.rotas = rotas;
    }

    public int getTotalMarmitasEntregues() {
        return totalMarmitasEntregues;
    }

    public void setTotalMarmitasEntregues(int totalMarmitasEntregues) {
        this.totalMarmitasEntregues = totalMarmitasEntregues;
    }
}
