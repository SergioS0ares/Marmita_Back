package com.j2ns.backend.repositories.auth;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.j2ns.backend.models.auth.ClientModel;

public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
/*
	public List<ClientModel> getAllClients();
	public Optional<ClientModel> getClientById(UUID id);
	public List<ClientModel> saveAllClients(List<ClientModel> list);
	public ResponseEntity<ClientModel> saveClient(ClientModel client);
*/
}
