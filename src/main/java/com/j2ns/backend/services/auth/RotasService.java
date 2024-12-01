package com.j2ns.backend.services.auth;

import com.j2ns.backend.config.Entregador;
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

    private Entregador entregador; // Para controlar a quantidade de marmitas do entregador

    public void calcularRotas(List<Map<String, Object>> rotasComCapacidade) {
        List<RotasModel> rotas = new ArrayList<>();
        int capacidadeMarmitas = 0;

        // Processar a lista para separar a capacidade e criar os objetos RotasModel
        for (Map<String, Object> item : rotasComCapacidade) {
            // Verificar se o item contém a chave "capacidadeMarmitas"
            if (item.containsKey("capacidadeMarmitas")) {
                capacidadeMarmitas = (int) item.get("capacidadeMarmitas");
            } else {
                // Mapear o objeto para RotasModel
                RotasModel rota = new RotasModel();
                rota.setNome((String) item.get("nome"));
                rota.setLatitude((String) item.get("latitude"));
                rota.setLongitude((String) item.get("longitude"));
                rota.setQuantidadeMarmitas((int) item.get("quantidadeMarmitas"));
                rota.setDistanciaViagem((double) item.get("distanciaViagem"));
                rota.setTempoViagem((double) item.get("tempoViagem"));
                rota.setSujestH((String) item.get("sujestH"));

                // insira aqui
                
                rotas.add(rota);
            }
            
            salvarRotaNoBanco();
        }

        // Salvar as rotas processadas e a capacidade do entregador
        this.rotasFront.addAll(rotas);
        entregador = new Entregador(); // Criar novo entregador
        entregador.setQuantMarmitaEntregador(capacidadeMarmitas);
    }
    
    private void salvarRotaNoBanco() {
        // Apagar todos os registros existentes
        testRepo.deleteAll();

        // Criar uma nova entidade para salvar
        TesteEntity testeEntity = new TesteEntity();
        testeEntity.setMsg("");
        testeEntity.setMsg("" + entregador.getQuantMarmitaEntregador());

        // Salvar a entidade no banco de dados
        testRepo.save(testeEntity);
    }


    public JSONobjectRotas getDestinos() {
        rotasDestinos.clear();

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
        rotasFinal.add(menorDistancia); // Adicionar diretamente na lista final

        // Variáveis auxiliares
        double somaTempo = menorDistancia.getTempoViagem();
        int capacidadeEntregador = entregador.getQuantMarmitaEntregador();

        // Verificar se a quantidade de marmitas excede a capacidade do entregador
        if (capacidadeEntregador < menorDistancia.getQuantidadeMarmitas()) {
            int restanteMarmitas = menorDistancia.getQuantidadeMarmitas() - capacidadeEntregador;
            menorDistancia.setQuantidadeMarmitas(capacidadeEntregador);
            entregador.setQuantMarmitaEntregador(0); // Zera as marmitas no entregador
            rotasFinal.add(restauranteRota); // Adiciona o restaurante no início
            rotasFinal.add(menorDistancia); // Adiciona o destino com marmitas entregues

            // Cria o ponto incompleto para retorno futuro
            RotasModel pontoIncompleto = new RotasModel();
            pontoIncompleto.setLatitude(menorDistancia.getLatitude());
            pontoIncompleto.setLongitude(menorDistancia.getLongitude());
            pontoIncompleto.setQuantidadeMarmitas(restanteMarmitas);
            rotasDestinos.add(pontoIncompleto);
        } else {
            entregador.setQuantMarmitaEntregador(capacidadeEntregador - menorDistancia.getQuantidadeMarmitas());
            rotasFinal.add(restauranteRota); // Adiciona o restaurante
            rotasFinal.add(menorDistancia); // Adiciona o destino com menor distância
        }
        return new JSONobjectRotas(rotasDestinos, entregador.getQuantMarmitaEntregador());
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
