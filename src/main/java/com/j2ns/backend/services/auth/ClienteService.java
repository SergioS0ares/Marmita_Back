package com.fachter.backend.services.auth;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fachter.backend.models.auth.ClientModel;
import com.fachter.backend.repositories.auth.ClientRepository;

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
}
