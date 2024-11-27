package com.fachter.backend.repositories.auth;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import com.fachter.backend.models.auth.ClientModel;

public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
	
}
