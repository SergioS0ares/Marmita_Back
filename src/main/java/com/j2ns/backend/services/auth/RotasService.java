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
        // Salvar a lista recebida no banco
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

            // Geração de IDs aleatórios no momento de criação
            if (rota.getId() == null) {
                rota.setId(UUID.randomUUID().toString());
            }

            rotas.add(rota);
        }

        // Salvar elementos no banco
        rotaRepo.saveAll(rotas);
        
        for(int i = 0; i <= rotas.size(); i++) {
        	if(rotas.get(i).getId() == null) {
        		TesteEntity ent = new TesteEntity();
        		ent.setMsg("nao foi");
        		testRepo.save(ent);
        	} else {
        		TesteEntity ent = new TesteEntity();
        		ent.setMsg("foi assim: " + ent.getMsg());
        		testRepo.save(ent);
        	}
        }

        // Recuperar todos os elementos do banco e armazená-los em lista1
        lista1 = rotaRepo.findAll();

        // Apagar os elementos do banco para evitar duplicações
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
        // Utilizar lista1 em vez de acessar o banco diretamente
        TrajetoModel trajeto = new TrajetoModel();
        trajeto.setRotas(lista1);
        trajeto.setTotalMarmitasEntregues(entregador.getQuantMarmitaEntregador());
        trajRepo.save(trajeto);

        return lista1;
    }
}
