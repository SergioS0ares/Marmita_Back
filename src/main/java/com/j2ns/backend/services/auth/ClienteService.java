package com.j2ns.backend.services.auth;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.j2ns.backend.models.auth.ClientModel;
import com.j2ns.backend.repositories.auth.ClientRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClientRepository repo;
	
	public List<ClientModel> getAllClients() {
        return repo.findAll();
    }
	
	public Optional<ClientModel> getClientById(UUID id){
		return repo.findById(id);
	}
	
	public List<ClientModel> saveAllClients(List<ClientModel> list){
		return repo.saveAll(list);
	}
	
	public ClientModel saveClient(ClientModel client) {
		return repo.save(client);
	}
	
	public void deleteClientById(UUID id) {
	    if (repo.existsById(id)) {
	        repo.deleteById(id);
	    } else {
	        throw new IllegalArgumentException("Cliente com o ID " + id + " não encontrado.");
	    }
	}
	
	public void deleteAllClients() {
	    repo.deleteAll();
	}
	
	public ClientModel updateClient(UUID id, ClientModel updatedClient) {
	    return repo.findById(id).map(existingClient -> {
	        existingClient.setNome(updatedClient.getNome());
	        existingClient.setDescricaoEndereco(updatedClient.getDescricaoEndereco());
	        existingClient.setQuantPedido(updatedClient.getQuantPedido());
	        existingClient.setTelefone(updatedClient.getTelefone());
	        existingClient.setLatitude(updatedClient.getLatitude());
	        existingClient.setLongitude(updatedClient.getLongitude());
	        existingClient.setSujestH(updatedClient.getSujestH());
	        return repo.save(existingClient); // Salva as alterações no banco
	    }).orElseThrow(() -> new IllegalArgumentException("Cliente com ID " + id + " não encontrado."));
	}
}
