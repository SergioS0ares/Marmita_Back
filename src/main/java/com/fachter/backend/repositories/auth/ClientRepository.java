package com.fachter.backend.repositories.auth;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fachter.backend.models.auth.ClientModel;

public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
	public List<ClientModel> getAllClients();
}
