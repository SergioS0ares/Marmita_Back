package com.j2ns.backend.services.auth;

import com.j2ns.backend.config.Entregador;
import com.j2ns.backend.config.JSONObjectRotasFront;
import com.j2ns.backend.config.JSONobjectRotas;
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

    private List<RotasModel> rotasFront = new ArrayList<>();
    private List<RotasModel> rotasDestinos = new ArrayList<>();
    private List<RotasModel> rotasFinal = new ArrayList<>();
    private static final int TEMPO_MAXIMO = 240;

    private Entregador entregador;

    public void calcularRotas(List<Map<String, Object>> rotasComCapacidade) {
        List<RotasModel> rotas = new ArrayList<>();
        int capacidadeMarmitas = 0;

        for (Map<String, Object> item : rotasComCapacidade) {
            if (item.containsKey("capacidadeMarmitas")) {
                capacidadeMarmitas = (int) item.get("capacidadeMarmitas");
            }

            RotasModel rota = new RotasModel();
            rota.setNome((String) item.get("nome"));
            rota.setLatitude((String) item.get("latitude"));
            rota.setLongitude((String) item.get("longitude"));
            rota.setQuantidadeMarmitas((int) item.get("quantidadeMarmitas"));
            rota.setDistanciaViagem((double) item.get("distanciaViagem"));
            rota.setTempoViagem((double) item.get("tempoViagem"));
            rota.setSujestH((String) item.get("sujestH"));

            rotas.add(rota);
        }

        this.rotasFront.addAll(rotas);
        entregador = new Entregador();
        entregador.setQuantMarmitaEntregador(capacidadeMarmitas);

        rotaRepo.saveAll(rotasFront); // Salvar a lista no banco
        rotasFront.clear(); // Limpar a lista após salvar
    }

    public List<JSONObjectRotasFront> getDestinos() {
        List<JSONObjectRotasFront> destinosAdaptados = new ArrayList<>();
        rotasDestinos.clear();

        List<RotasModel> rotasBanco = rotaRepo.findAll(); // Puxar do banco
        if (rotasBanco.isEmpty()) {
            return destinosAdaptados; // Retornar vazio se não houver rotas
        }

        RestauranteModel restaurante = restRepo.findAll().get(0);
        RotasModel restauranteRota = new RotasModel();
        restauranteRota.setLatitude(restaurante.getLatitudeRestaurante());
        restauranteRota.setLongitude(restaurante.getLongitudeRestaurante());
        restauranteRota.setNome("Restaurante");

        rotasBanco.sort(Comparator.comparingDouble(RotasModel::getDistanciaViagem));

        RotasModel destinoAtual = null;
        double tempoTotal = 0;

        for (RotasModel rota : rotasBanco) {
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

        rotasDestinos.add(destinoAtual);
        rotasBanco.remove(destinoAtual);

        for (RotasModel rota : rotasBanco) {
            if (!rotasDestinos.contains(rota)) {
                rotasDestinos.add(rota);
            }
        }

        for (RotasModel rota : rotasDestinos) {
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
        List<RotasModel> rotasBanco = rotaRepo.findAll(); // Carregar do banco

        TrajetoModel trajeto = new TrajetoModel();
        trajeto.setRotas(rotasBanco);
        trajeto.setTotalMarmitasEntregues(entregador.getQuantMarmitaEntregador());
        trajRepo.save(trajeto);

        rotasFinal.clear();
        rotasFinal.addAll(rotasBanco);

        return rotasFinal;
    }
}
