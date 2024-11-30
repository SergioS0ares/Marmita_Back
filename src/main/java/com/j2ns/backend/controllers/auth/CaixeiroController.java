package com.j2ns.backend.controllers.auth;

import com.j2ns.backend.utils.EntregaDTO;
import com.j2ns.backend.utils.RotaDTO;
import com.j2ns.backend.utils.UtilsCaixeiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController // Indica que essa classe será um controlador REST no Spring.
public class CaixeiroController {

    @Autowired // Injeção de dependência, a classe UtilsCaixeiro será automaticamente instanciada pelo Spring.
    private UtilsCaixeiro utilsCaixeiro;

    // Método mapeado para a URL "/rota", que irá retornar a melhor rota para entregas.
    @GetMapping("/rota")
    public List<RotaDTO> obterRotas() {
        List<EntregaDTO> todasEntregas = Arrays.asList(
                new EntregaDTO(1, 1, 1, 4), 
                new EntregaDTO(2, 2, 3, 3), 
                new EntregaDTO(3, 3, 2, 8), 
                new EntregaDTO(4, 4, 7, 2), 
                new EntregaDTO(5, 5, 6, 6)
        );

        return utilsCaixeiro.calcularRotas(todasEntregas);
    }

}
