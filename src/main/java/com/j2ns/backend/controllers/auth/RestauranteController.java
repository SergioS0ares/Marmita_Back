package com.j2ns.backend.controllers.auth;

import com.j2ns.backend.models.auth.RestauranteModel;
import com.j2ns.backend.services.auth.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pontoInicial")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    /**
     * Endpoint para obter as coordenadas do restaurante. GET /api/restaurante/coordenadas
     */
    @GetMapping("/coordenadas")
    public Optional<RestauranteModel> getCoordenadas() {
        return restauranteService.getCoordenadas();
    }

    /**
     * Endpoint para atualizar as coordenadas do restaurante. POST /api/restaurante/coordenadas
     */
    @PostMapping("/coordenadas")
    public Optional<RestauranteModel> updateCoordenadas(@RequestBody RestauranteModel restauranteModel) {
        return restauranteService.updateCoordenadas(
                restauranteModel.getLatitudeRestaurante(),
                restauranteModel.getLongitudeRestaurante()
        );
    }
}
