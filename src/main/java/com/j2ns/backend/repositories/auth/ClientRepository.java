package com.j2ns.backend.repositories.auth;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.j2ns.backend.models.auth.ClientModel;

public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
	
}
