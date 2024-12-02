package com.j2ns.backend.services.auth;

import com.j2ns.backend.config.Entregador;
import com.j2ns.backend.config.JSONObjectRotasFront;
import com.j2ns.backend.mocks.TesteEntity;
import com.j2ns.backend.mocks.TesteRepository;
import com.j2ns.backend.models.auth.RotasModel;
import com.j2ns.backend.models.auth.TrajetoModel;
import com.j2ns.backend.models.auth.RestauranteModel;
import com.j2ns.backend.repositories.auth.RestauranteRepository;
import com.j2ns.backend.repositories.auth.RotasRepository;
import com.j2ns.backend.repositories.auth.TrajetoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RotasService {

    @Autowired
    private RestauranteRepository restRepo;

    @Autowired
    private RotasRepository rotaRepo;

    @Autowired
    private TrajetoRepository trajRepo;
    
    @Autowired
    private TesteRepository testRepo;

    private List<RotasModel> lista1 = new ArrayList<>();
    private static final int TEMPO_MAXIMO = 240;

    private Entregador entregador;

    public void calcularRotas(List<Map<String, Object>> rotasComCapacidade) {
        List<RotasModel> rotas = new ArrayList<>();
        int capacidadeMarmitas = 0;

        for (Map<String, Object> item : rotasComCapacidade) {
            if (item.containsKey("capacidadeMarmitas")) {
                capacidadeMarmitas = (int) item.getOrDefault("capacidadeMarmitas", 0);
            }

            RotasModel rota = new RotasModel();
            rota.setNome((String) item.get("nome"));
            rota.setLatitude((String) item.get("latitude"));
            rota.setLongitude((String) item.get("longitude"));
            rota.setQuantidadeMarmitas((int) item.getOrDefault("quantidadeMarmitas", 0));
            rota.setDistanciaViagem(((Number) item.getOrDefault("distanciaViagem", 0.0)).doubleValue());
            rota.setTempoViagem(((Number) item.getOrDefault("tempoViagem", 0.0)).doubleValue());
            rota.setSujestH((String) item.get("sujestH"));

            // Gera ID se não existir
            if (rota.getId() == null) {
                rota.setId(UUID.randomUUID().toString());
            }

            rotas.add(rota);
        }

        // Salvar no banco
        rotaRepo.saveAll(rotas);

        // Teste adicional para salvar mensagens
        for (RotasModel rota : rotas) {
            TesteEntity ent = new TesteEntity();
            ent.setMsg("ID salvo: " + rota.getId());
            testRepo.save(ent);
        }

        lista1 = rotaRepo.findAll();
        rotaRepo.deleteAll();

        entregador = new Entregador();
        entregador.setQuantMarmitaEntregador(capacidadeMarmitas);
    }



    public List<JSONObjectRotasFront> getDestinos() {
        // Utilizar lista1 em vez de acessar o banco diretamente
        List<JSONObjectRotasFront> destinosAdaptados = new ArrayList<>();

        if (lista1.isEmpty()) {
            return destinosAdaptados; // Retornar vazio se não houver rotas
        }

        RestauranteModel restaurante = restRepo.findAll().get(0);
        RotasModel restauranteRota = new RotasModel();
        restauranteRota.setLatitude(restaurante.getLatitudeRestaurante());
        restauranteRota.setLongitude(restaurante.getLongitudeRestaurante());
        restauranteRota.setNome("Restaurante");

        lista1.sort(Comparator.comparingDouble(RotasModel::getDistanciaViagem));

        RotasModel destinoAtual = null;
        double tempoTotal = 0;

        for (RotasModel rota : lista1) {
            tempoTotal += rota.getTempoViagem();
            if (tempoTotal <= TEMPO_MAXIMO) {
                destinoAtual = rota;
                break;
            }
        }

        if (destinoAtual == null) {
            JSONObjectRotasFront restauranteJson = new JSONObjectRotasFront();
            restauranteJson.setNome("Restaurante");
            restauranteJson.setLatitude(restaurante.getLatitudeRestaurante());
            restauranteJson.setLongitude(restaurante.getLongitudeRestaurante());
            restauranteJson.setQuantidadeMarmitas(0);
            restauranteJson.setDistanciaViagem(0);
            restauranteJson.setTempoViagem(0);
            restauranteJson.setSujestH("N/A");
            restauranteJson.setCapacidadeMarmitas(entregador.getQuantMarmitaEntregador());

            destinosAdaptados.add(restauranteJson);
            return destinosAdaptados;
        }

        int marmitasEntregues = Math.min(entregador.getQuantMarmitaEntregador(), destinoAtual.getQuantidadeMarmitas());
        destinoAtual.setQuantidadeMarmitas(destinoAtual.getQuantidadeMarmitas() - marmitasEntregues);
        entregador.setQuantMarmitaEntregador(entregador.getQuantMarmitaEntregador() - marmitasEntregues);

        lista1.remove(destinoAtual);

        for (RotasModel rota : lista1) {
            JSONObjectRotasFront jsonRota = new JSONObjectRotasFront();
            jsonRota.setNome(rota.getNome());
            jsonRota.setLatitude(rota.getLatitude());
            jsonRota.setLongitude(rota.getLongitude());
            jsonRota.setQuantidadeMarmitas(rota.getQuantidadeMarmitas());
            jsonRota.setDistanciaViagem(rota.getDistanciaViagem());
            jsonRota.setTempoViagem(rota.getTempoViagem());
            jsonRota.setSujestH(rota.getSujestH());
            jsonRota.setCapacidadeMarmitas(entregador.getQuantMarmitaEntregador());

            destinosAdaptados.add(jsonRota);
        }

        return destinosAdaptados;
    }

    public List<RotasModel> getRotas() {
        // Reanexar entidades "detached" ao contexto do Hibernate
        List<RotasModel> rotasGerenciadas = rotaRepo.saveAll(lista1);

        // Criar o TrajetoModel com as rotas gerenciadas
        TrajetoModel trajeto = new TrajetoModel();
        trajeto.setRotas(rotasGerenciadas); // Usar entidades gerenciadas pelo contexto
        trajeto.setTotalMarmitasEntregues(entregador.getQuantMarmitaEntregador());

        // Salvar o trajeto no banco
        trajRepo.save(trajeto);

        return lista1;
    }

}
