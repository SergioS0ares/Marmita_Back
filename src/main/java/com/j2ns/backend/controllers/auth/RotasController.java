package com.j2ns.backend.controllers.auth;

import com.j2ns.backend.config.JSONObjectRotasFront;
import com.j2ns.backend.models.auth.RotasModel;
import com.j2ns.backend.services.auth.RotasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rotas")
public class RotasController {

    @Autowired
    private RotasService serv;

    // Requisição POST para calcular as rotas com uma lista de RotasModel e dados do Entregador
    @PostMapping("/calcularRotas")
    public ResponseEntity<Void> receberRotas(@RequestBody List<Map<String, Object>> rotasComCapacidade) {
        try {
            serv.calcularRotas(rotasComCapacidade);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Requisição GET para obter os destinos e restante das marmitas do entregador
    @GetMapping("/getDestino")
    public List<JSONObjectRotasFront> getDestino() {
        return serv.getDestinos(); // Retorna o JSONobjectRotas com a lista de destinos e status do entregador
    }

    // Requisição GET para obter a lista final de rotas após o cálculo
    @GetMapping("/getRotas")
    public List<RotasModel> getRotas() {
        return serv.getRotas(); // Retorna a lista final calculada
    }
}
