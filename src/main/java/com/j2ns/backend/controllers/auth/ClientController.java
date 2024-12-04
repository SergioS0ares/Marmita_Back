package com.j2ns.backend.controllers.auth;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.j2ns.backend.models.auth.ClientModel;
import com.j2ns.backend.services.auth.ClienteService;

@RestController
@RequestMapping("/client")
public class ClientController {

	
	/*
	 * Objeto do service, saiba que sem isso, o controller simplesmente nao roda!
	 * */ 
	@Autowired
	private ClienteService serv;
	
	
	// requisiçao que puxa todos os clientes salvos no banco de dados em uma lista de object JSON
	@GetMapping("/getAllclients")
	public List<ClientModel> getAllClients(){
		return serv.getAllClients();
	}
	
	// requisiçao que puxa um cliente expecifico do banco de dados em JSON
	@GetMapping("/getClientById/{id}")
	public Optional<ClientModel> getClientById(@PathVariable UUID id){
		return serv.getClientById(id);
	}
	
	// requisiçao que salva vários clientes de uma vez no banco
	@PostMapping("/saveAllClients") 
	public ResponseEntity<List<ClientModel>> saveAllClients(@RequestBody List<ClientModel> list){
		List<ClientModel> saveList = serv.saveAllClients(list);
		return ResponseEntity.ok(saveList);
	}
	
	// requisiçao que salva um unico cliente no bando
	@PostMapping("/saveClient") 
	public ResponseEntity<ClientModel> saveClient(@RequestBody ClientModel client){
		ClientModel savedClient = serv.saveClient(client);
		return ResponseEntity.ok(savedClient);
	}

	// requisiçao de deleçao de cliente, por id
	@DeleteMapping("/deleteClient/{id}") 
	public ResponseEntity<String> deleteClient(@PathVariable UUID id) {
	    try {
	        serv.deleteClientById(id);
	        return ResponseEntity.ok("Cliente com ID " + id + " foi deletado com sucesso.");
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(404).body(e.getMessage());
	    }
	}
	
	/*
	 * requisiçao que atualiza clientes no banco de dados usando a ID como chave de identificaçao,
	 * tivemos que fazer uma depois de uma correçao de um erro de logica nas ids entre angular e Spring
	 * */ 
	@PutMapping("/updateClient/{id}")
	public ResponseEntity<ClientModel> updateClient(@PathVariable UUID id, @RequestBody ClientModel client) {
	    try {
	        ClientModel updatedClient = serv.updateClient(id, client);
	        return ResponseEntity.ok(updatedClient);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(404).body(null); // Cliente não encontrado
	    }
	}
	
	
	//				ATENCAO//				ATENCAO//				ATENCAO//				ATENCAO
	
	/*
	 * 		Esse metodo é unica e exclusivamete para fins de teste, ou se vc tiver querendo, MUITO
	 * 		matar todo mundo no banco de dados, nao aconselho colocar nada disso no front, apesar
	 * 		de que esse projeto ser só daqui da faculdade, seria meio antiprofissionalismo deixar
	 * 		algo tao poderoso na mao do cliente		;)
	 * */
	@DeleteMapping("teste/tenhoCerteza/deleteAllClients")
	public ResponseEntity<String> deleteAllClients() {
	    serv.deleteAllClients();
	    return ResponseEntity.ok("Todos os clientes foram deletados com sucesso.");
	}
}
