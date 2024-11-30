package com.j2ns.backend.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.j2ns.backend.models.auth.RestauranteModel;
import com.j2ns.backend.repositories.auth.RestauranteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository repo;

    /**
     * Método para obter as coordenadas do restaurante.
     * Retorna um Optional com o restaurante ou vazio se não encontrar.
     */
    public Optional<RestauranteModel> getCoordenadas() {
        List<RestauranteModel> restaurantes = repo.findAll();
        if (!restaurantes.isEmpty()) {
            return Optional.of(restaurantes.get(0));
        }
        return Optional.empty();
    }

    /**
     * Método para atualizar ou salvar o restaurante com as novas coordenadas.
     * Se as informações forem diferentes das salvas, deleta tudo e salva o novo restaurante.
     * Se forem iguais, ignora a requisição.
     */
    public Optional<RestauranteModel> updateCoordenadas(String latitudeRestaurante, String longitudeRestaurante) {
        // Busca todos os restaurantes no banco
        List<RestauranteModel> restaurantes = repo.findAll();

        // Verifica se já existe um restaurante salvo
        if (!restaurantes.isEmpty()) {
            RestauranteModel restauranteExistente = restaurantes.get(0);

            // Verifica se as informações são iguais
            if (restauranteExistente.getLatitudeRestaurante().equals(latitudeRestaurante) &&
                restauranteExistente.getLongitudeRestaurante().equals(longitudeRestaurante)) {
                // Se forem iguais, ignora e retorna o restaurante existente
                return Optional.of(restauranteExistente);
            }

            // Se forem diferentes, apaga todos os registros
            repo.deleteAll();
        }

        // Salva o novo restaurante com as coordenadas recebidas
        RestauranteModel novoRestaurante = new RestauranteModel();
        novoRestaurante.setLatitudeRestaurante(latitudeRestaurante);
        novoRestaurante.setLongitudeRestaurante(longitudeRestaurante);
        repo.save(novoRestaurante);

        return Optional.of(novoRestaurante);
    }
}
