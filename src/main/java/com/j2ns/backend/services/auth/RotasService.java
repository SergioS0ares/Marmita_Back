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
    private List<Map<String, Object>> listTeste = new ArrayList<>();
    private static final int TEMPO_MAXIMO = 240;

    private Entregador entregador; // Para controlar a quantidade de marmitas do entregador

    public void calcularRotas(List<Map<String, Object>> rotasComCapacidade) {
        List<RotasModel> rotas = new ArrayList<>();
        int capacidadeMarmitas = 0;

        // Processar a lista para separar a capacidade e criar os objetos RotasModel
        for (Map<String, Object> item : rotasComCapacidade) {
            // Capturar capacidade de marmitas se disponível
            if (item.containsKey("capacidadeMarmitas")) {
                capacidadeMarmitas = (int) item.get("capacidadeMarmitas");
            }

            // Mapear o restante do objeto para RotasModel
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

        // Salvar as rotas processadas e a capacidade do entregador
        this.rotasFront.addAll(rotas);
        entregador = new Entregador(); // Criar novo entregador
        entregador.setQuantMarmitaEntregador(capacidadeMarmitas);

        salvarRotaNoBanco();
    }

    
    private void salvarRotaNoBanco() {
        // Apagar todos os registros existentes
        testRepo.deleteAll();

        // Criar uma nova entidade para salvar
        TesteEntity testeEntity = new TesteEntity();

        if (!rotasFront.isEmpty()) {
            testeEntity.setMsg(rotasFront.get(0).getNome());
        } else {
            testeEntity.setMsg("Nenhuma rota disponível");
        }

        testeEntity.setMsg(testeEntity.getMsg() + "/ / /" + entregador.getQuantMarmitaEntregador());
        
        testeEntity.setMsg(testeEntity.getMsg() + "/ / /" + listTeste.size());

        // Salvar a entidade no banco de dados
        testRepo.save(testeEntity);
    }


    

    public List<JSONObjectRotasFront> getDestinos() {
        List<JSONObjectRotasFront> destinosAdaptados = new ArrayList<>();
        rotasDestinos.clear();

        // Obter o restaurante da base de dados
        RestauranteModel restaurante = restRepo.findAll().get(0); // Assume que só há um restaurante
        RotasModel restauranteRota = new RotasModel();
        restauranteRota.setLatitude(restaurante.getLatitudeRestaurante());
        restauranteRota.setLongitude(restaurante.getLongitudeRestaurante());
        restauranteRota.setNome("Restaurante");

        // Ordenar rotas pela menor distância
        rotasFront.sort(Comparator.comparingDouble(RotasModel::getDistanciaViagem));

        // Encontrar o próximo destino dentro do limite de tempo
        RotasModel destinoAtual = null;
        double tempoTotal = 0;

        for (RotasModel rota : rotasFront) {
            tempoTotal += rota.getTempoViagem();
            if (tempoTotal <= TEMPO_MAXIMO) {
                destinoAtual = rota;
                break;
            }
        }

        // Se não houver um destino válido, retorne lista vazia
        if (destinoAtual == null) {
            return destinosAdaptados; 
        }

        // Calcular marmitas entregues
        int marmitasEntregues = Math.min(entregador.getQuantMarmitaEntregador(), destinoAtual.getQuantidadeMarmitas());
        destinoAtual.setQuantidadeMarmitas(destinoAtual.getQuantidadeMarmitas() - marmitasEntregues);
        entregador.setQuantMarmitaEntregador(entregador.getQuantMarmitaEntregador() - marmitasEntregues);

        // Adicionar o destino atual em primeiro lugar
        rotasDestinos.add(destinoAtual);

        // Remover ou atualizar o destino na lista original
        if (destinoAtual.getQuantidadeMarmitas() > 0) {
            rotasFront.remove(destinoAtual);
            rotasFront.add(0, destinoAtual); // Reinsere no início para manter prioridade
        } else {
            rotasFront.remove(destinoAtual);
        }

        // Adicionar os destinos restantes
        rotasDestinos.addAll(rotasFront);

        // Adaptar as rotas para JSONObjectRotasFront
        for (RotasModel rota : rotasDestinos) {
            JSONObjectRotasFront jsonRota = new JSONObjectRotasFront();
            jsonRota.setNome(rota.getNome());
            jsonRota.setLatitude(rota.getLatitude());
            jsonRota.setLongitude(rota.getLongitude());
            jsonRota.setQuantidadeMarmitas(rota.getQuantidadeMarmitas());
            jsonRota.setDistanciaViagem(rota.getDistanciaViagem());
            jsonRota.setTempoViagem(rota.getTempoViagem());
            jsonRota.setSujestH(rota.getSujestH());
            jsonRota.setCapacidadeMarmitas(entregador.getQuantMarmitaEntregador()); // Capacidade atualizada

            destinosAdaptados.add(jsonRota);
        }

        return destinosAdaptados;
    }






    public List<RotasModel> getRotas() {

        // Salvar lista final no banco usando uma classe intermediária
        TrajetoModel trajeto = new TrajetoModel();
        trajeto.setRotas(new ArrayList<>(rotasFinal)); // Copiar lista para evitar modificações posteriores
        trajeto.setTotalMarmitasEntregues(entregador.getQuantMarmitaEntregador());
        trajRepo.save(trajeto); // Salva o trajeto como um único objeto no banco

        List<RotasModel> rotasRetorno = rotasFinal;
        rotasFinal.clear();

        return rotasRetorno;
    }
}
