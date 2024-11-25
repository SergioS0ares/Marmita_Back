package com.fachter.backend.services.auth;

import java.util.List;

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
}
