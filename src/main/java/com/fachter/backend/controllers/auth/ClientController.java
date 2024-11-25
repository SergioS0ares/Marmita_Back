package com.fachter.backend.controllers.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fachter.backend.models.auth.ClientModel;
import com.fachter.backend.repositories.auth.ClientRepository;
import com.fachter.backend.services.auth.ClienteService;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private ClientRepository repo;
	@Autowired
	private ClienteService serv;
	
	@GetMapping("/getAllclients")
	public List<ClientModel> getAllClients(){
		return serv.getAllClients();
	}
}
