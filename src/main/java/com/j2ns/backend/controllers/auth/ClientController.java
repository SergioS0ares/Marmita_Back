package com.j2ns.backend.controllers.auth;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.j2ns.backend.models.auth.ClientModel;
import com.j2ns.backend.services.auth.ClienteService;

@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClienteService serv;
	
	@GetMapping("/getAllclients")
	public List<ClientModel> getAllClients(){
		return serv.getAllClients();
	}
	
	@GetMapping("/getClientById/{id}")
	public Optional<ClientModel> getClientById(@PathVariable UUID id){
		return serv.getClientById(id);
	}
	
	@PostMapping("/saveAllClients")
	public ResponseEntity<List<ClientModel>> saveAllClients(@RequestBody List<ClientModel> list){
		List<ClientModel> saveList = serv.saveAllClients(list);
		return ResponseEntity.ok(saveList);
	}
	
	@PostMapping("/saveClient")
	public ResponseEntity<ClientModel> saveClient(@RequestBody ClientModel client){
		ClientModel savedClient = serv.saveClient(client);
		return ResponseEntity.ok(savedClient);
	}
}
