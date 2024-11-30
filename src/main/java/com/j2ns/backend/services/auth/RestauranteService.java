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
        // Aqui, vamos pegar o primeiro restaurante encontrado. Se houver mais de um, você pode ajustar conforme necessário.
        List<RestauranteModel> restaurantes = repo.findAll();
        
        // Se o banco tiver pelo menos um restaurante, retorna o primeiro.
        if (!restaurantes.isEmpty()) {
            return Optional.of(restaurantes.get(0));
        }
        
        return Optional.empty();  // Caso não haja nenhum restaurante, retorna Optional vazio.
    }

    
    
    
    /**
     * Método para atualizar ou salvar o restaurante com as novas coordenadas.
     * Se já existir um restaurante com a mesma latitude, ele será atualizado.
     * Caso contrário, será criado um novo.
     */
    public Optional<RestauranteModel> updateCoordenadas(String latitudeRestaurante, String longitudeRestaurante) {
        // Busca todos os restaurantes
        List<RestauranteModel> todosRestaurantes = repo.findAll();
        
        // Filtra os restaurantes com a mesma latitude (e opcionalmente, longitude)
        List<RestauranteModel> restaurantesComCoordenadas = todosRestaurantes.stream()
                .filter(r -> r.getLatitudeRestaurante().equals(latitudeRestaurante))
                .toList();
        
        // Se houver mais de um restaurante com a mesma latitude, vamos apagar todos
        if (restaurantesComCoordenadas.size() > 1) {
            repo.deleteAll(restaurantesComCoordenadas);  // Exclui todos os restaurantes encontrados
        }
        
        // Se encontrar um restaurante com essa latitude, ele será atualizado com a nova longitude
        if (!restaurantesComCoordenadas.isEmpty()) {
            RestauranteModel restauranteExistente = restaurantesComCoordenadas.get(0);
            restauranteExistente.setLongitudeRestaurante(longitudeRestaurante);
            repo.save(restauranteExistente);  // Atualiza o restaurante existente
            return Optional.of(restauranteExistente);
        } else {
            // Se não existir, cria um novo restaurante
            RestauranteModel novoRestaurante = new RestauranteModel();
            novoRestaurante.setLatitudeRestaurante(latitudeRestaurante);
            novoRestaurante.setLongitudeRestaurante(longitudeRestaurante);
            repo.save(novoRestaurante);  // Salva o novo restaurante
            return Optional.of(novoRestaurante);
        }
    }
}