package com.j2ns.backend.services.auth;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.j2ns.backend.models.auth.ClientModel;
import com.j2ns.backend.repositories.auth.ClientRepository;

@Service // Indica que esta classe é um componente do Spring que fornece lógica de negócios (um serviço).
public class ClienteService {

    @Autowired // Injeta automaticamente a dependência do repositório de clientes.
    private ClientRepository repo;

    // Retorna todos os clientes do banco de dados.
    public List<ClientModel> getAllClients() {
        return repo.findAll(); // Usa o método findAll do repositório para buscar todos os registros.
    }

    // Busca um cliente específico pelo ID.
    public Optional<ClientModel> getClientById(UUID id) {
        return repo.findById(id); // Retorna um Optional contendo o cliente, caso encontrado.
    }

    // Salva uma lista de clientes no banco de dados.
    public List<ClientModel> saveAllClients(List<ClientModel> list) {
        return repo.saveAll(list); // Usa o método saveAll do repositório para salvar todos os clientes fornecidos.
    }

    // Salva um único cliente no banco de dados.
    public ClientModel saveClient(ClientModel client) {
        return repo.save(client); // Usa o método save do repositório para persistir o cliente.
    }

    // Exclui um cliente do banco pelo ID.
    public void deleteClientById(UUID id) {
        if (repo.existsById(id)) { // Verifica se o cliente existe no banco antes de tentar excluí-lo.
            repo.deleteById(id); // Remove o cliente pelo ID.
        } else {
            throw new IllegalArgumentException("Cliente com o ID " + id + " não encontrado."); // Lança uma exceção se o cliente não existir.
        }
    }

    // Exclui todos os clientes do banco de dados.
    public void deleteAllClients() {
        repo.deleteAll(); // Remove todos os registros da tabela de clientes.
    }

    // Atualiza os dados de um cliente existente.
    public ClientModel updateClient(UUID id, ClientModel updatedClient) {
        return repo.findById(id).map(existingClient -> { // Busca o cliente pelo ID.
            // Atualiza os campos do cliente existente com os valores fornecidos.
            existingClient.setNome(updatedClient.getNome());
            existingClient.setDescricaoEndereco(updatedClient.getDescricaoEndereco());
            existingClient.setQuantPedido(updatedClient.getQuantPedido());
            existingClient.setTelefone(updatedClient.getTelefone());
            existingClient.setLatitude(updatedClient.getLatitude());
            existingClient.setLongitude(updatedClient.getLongitude());
            existingClient.setSujestH(updatedClient.getSujestH());
            return repo.save(existingClient); // Salva as alterações no banco e retorna o cliente atualizado.
        }).orElseThrow(() -> 
            new IllegalArgumentException("Cliente com ID " + id + " não encontrado.")
        ); // Lança uma exceção se o cliente não for encontrado.
    }
}
