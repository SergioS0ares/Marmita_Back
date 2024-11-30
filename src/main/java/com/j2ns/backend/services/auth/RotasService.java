package com.j2ns.backend.services.auth;

import com.j2ns.backend.models.auth.RotasModel;
import com.j2ns.backend.models.auth.RestauranteModel;
import com.j2ns.backend.repositories.auth.RestauranteRepository;
import com.j2ns.backend.repositories.auth.RotasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RotasService {

    @Autowired
    private RestauranteRepository restRepo;

    @Autowired
    private RotasRepository rotaRepo;

    private List<RotasModel> rotasFront = new ArrayList<>();
    private List<RotasModel> rotasDestinos = new ArrayList<>();
    private List<RotasModel> rotasFinal = new ArrayList<>();
    private static final int TEMPO_MAXIMO = 240;

    public void calcularRotas(List<RotasModel> rotas) {
        rotasFront.addAll(rotas);
    }

    public List<RotasModel> getDestinos() {
        rotasDestinos.clear();
        rotasFinal.clear();

        // Obter o restaurante da base de dados
        RestauranteModel restaurante = restRepo.findAll().get(0); // Assume que só há um restaurante
        RotasModel restauranteRota = new RotasModel();
        restauranteRota.setLatitude(restaurante.getLatitudeRestaurante());
        restauranteRota.setLongitude(restaurante.getLongitudeRestaurante());
        restauranteRota.setNome("Restaurante");

        // Ordenar rotas pela menor distância
        rotasFront.sort(Comparator.comparingDouble(RotasModel::getDistanciaViagem));

        // Obter o destino com menor distância
        RotasModel menorDistancia = rotasFront.get(0);
        rotasDestinos.add(menorDistancia);

        // Variáveis auxiliares
        double somaTempo = menorDistancia.getTempoViagem();
        int capacidadeEntregador = menorDistancia.getQuantMarmitaEntregador();

        // Verificar se a quantidade de marmitas excede a capacidade do entregador
        if (capacidadeEntregador < menorDistancia.getQuantidadeMarmitas()) {
            int restanteMarmitas = menorDistancia.getQuantidadeMarmitas() - capacidadeEntregador;
            menorDistancia.setQuantidadeMarmitas(restanteMarmitas); // Atualiza o destino
            rotasFinal.add(restauranteRota); // Adiciona o restaurante no início
            rotasFinal.add(menorDistancia); // Adiciona o destino com marmitas restantes
        } else {
            rotasFinal.add(restauranteRota); // Adiciona o restaurante
            rotasFinal.add(menorDistancia); // Adiciona o destino com menor distância
        }

        // Verificar tempo total
        for (RotasModel rota : rotasFront) {
            if (!rota.equals(menorDistancia) && rota.getQuantidadeMarmitas() > 0) {
                somaTempo += rota.getTempoViagem();
                if (somaTempo > TEMPO_MAXIMO) {
                    break;
                }
                rotasFinal.add(rota);
            }
        }

        // Persistir rotasFinal no banco como histórico
        rotaRepo.saveAll(rotasFinal);

        return rotasDestinos;
    }

    public List<RotasModel> getRotas() {
        return rotasFinal;
    }
}
