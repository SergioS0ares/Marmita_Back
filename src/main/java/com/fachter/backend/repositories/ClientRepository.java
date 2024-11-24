package com.fachter.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fachter.backend.models.auth.ClientModel;

public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
	public String ClientsAllToString();
}
