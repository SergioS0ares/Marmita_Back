package com.j2ns.backend.controllers.auth;

import com.j2ns.backend.utils.EntregaDTO;
import com.j2ns.backend.utils.RotaDTO;
import com.j2ns.backend.utils.UtilsCaixeiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class CaixeiroController {

    @Autowired
    private UtilsCaixeiro utilsCaixeiro;

    @GetMapping("/rota")
    public RotaDTO obterRota() {// Lista de entregas simuladas
        List<EntregaDTO> todasEntregas = Arrays.asList(
                new EntregaDTO(1, 1, 5, 4),
                new EntregaDTO(2, 2, 3, 3),
                new EntregaDTO(3, 3, 2, 8),
                new EntregaDTO(4, 4, 7, 2),
                new EntregaDTO(5, 5, 6, 6)
        );

        return utilsCaixeiro.calcularRota(todasEntregas);// Calcula a melhor rota
    }
}

