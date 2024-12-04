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

    private List<RotasModel> lista = new ArrayList<>();
    private static final int TEMPO_MAXIMO = 240;

    private Entregador entregador;

    public void calcularRotas(List<Map<String, Object>> rotasComCapacidade) {
        List<RotasModel> rotas = new ArrayList<>();
        int capacidadeMarmitas = 0;

        // Preparar as rotas com base nas informações recebidas
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

        // Salvar as novas rotas no banco de dados (sem deletar previamente)
        rotaRepo.saveAll(rotas);

        // Teste adicional para salvar mensagens
        for (RotasModel rota : rotas) {
            TesteEntity ent = new TesteEntity();
            ent.setMsg("ID salvo: " + rota.getId());
            testRepo.save(ent);
            
        }

        // Agora podemos consultar as rotas salvas do banco
        lista = rotaRepo.findAll();

        // Configuração do entregador
        entregador = new Entregador();
        entregador.setQuantMarmitaEntregador(capacidadeMarmitas);
    }

    public List<JSONObjectRotasFront> getDestinos() {
        List<JSONObjectRotasFront> destinosAdaptados = new ArrayList<>();

        // Certificar que a lista está atualizada a partir do banco de dados
        lista = rotaRepo.findAll();  // Atualiza a lista com os dados mais recentes do banco

        if (lista.isEmpty()) {
            return destinosAdaptados; // Retornar vazio se não houver rotas
        }

        // Organize as rotas pela menor distância de viagem
        lista.sort(Comparator.comparingDouble(RotasModel::getDistanciaViagem));

        // Adicionar o destino mais próximo primeiro
        for (RotasModel rota : lista) {
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
        List<RotasModel> rotasGerenciadas = rotaRepo.saveAll(lista);

        // Criar o TrajetoModel com as rotas gerenciadas
        TrajetoModel trajeto = new TrajetoModel();
        trajeto.setRotas(rotasGerenciadas); // Usar entidades gerenciadas pelo contexto
        trajeto.setTotalMarmitasEntregues(entregador.getQuantMarmitaEntregador());

        // Salvar o trajeto no banco
        trajRepo.save(trajeto);

        return lista;
    }

}
