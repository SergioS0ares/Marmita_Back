package com.j2ns.backend.controllers.auth;

import com.j2ns.backend.models.auth.RotasModel;
import com.j2ns.backend.services.auth.RotasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rotas")
public class RotasController {

    @Autowired
    private RotasService serv;

    // Requisição POST para salvar uma lista de RotasModel
    @PostMapping("/calcularRotas")
    public void calcularRotas(@RequestBody List<RotasModel> rotas) {
        serv.calcularRotas(rotas);
    }

    // Requisição GET para obter a lista2 calculada
    @GetMapping("/getDestino")
    public List<RotasModel> getDestino() {
        return serv.getDestinos();
    }

    // Requisição GET para retornar a lista3
    @GetMapping("/getRotas")
    public List<RotasModel> getRotas() {
        return serv.getRotas();
    }
}
