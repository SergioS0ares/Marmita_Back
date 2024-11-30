package com.j2ns.backend.repositories.auth;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.j2ns.backend.models.auth.RotasModel;

public interface RotasRepository extends JpaRepository<RotasModel, UUID>{

}
