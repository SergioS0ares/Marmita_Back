package com.fachter.backend.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fachter.backend.repositories.ClientRepository;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private ClientRepository repo;
	
	@GetMapping("/getALLclients")
	
}
