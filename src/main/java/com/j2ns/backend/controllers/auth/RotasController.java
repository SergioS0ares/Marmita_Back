package com.j2ns.backend.controllers.auth;

import com.j2ns.backend.config.JSONobjectRotas;
import com.j2ns.backend.models.auth.RotasModel;
import com.j2ns.backend.services.auth.RotasService;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void calcularRotas(@RequestBody List<Map<String, Object>> rotasComCapacidade) {
        serv.calcularRotas(rotasComCapacidade);
    }


    // Requisição GET para obter os destinos e restante das marmitas do entregador
    @GetMapping("/getDestino")
    public JSONobjectRotas getDestino() {
        return serv.getDestinos(); // Retorna o JSONobjectRotas com a lista de destinos e status do entregador
    }

    // Requisição GET para obter a lista final de rotas após o cálculo
    @GetMapping("/getRotas")
    public List<RotasModel> getRotas() {
        return serv.getRotas(); // Retorna a lista final calculada
    }
}
