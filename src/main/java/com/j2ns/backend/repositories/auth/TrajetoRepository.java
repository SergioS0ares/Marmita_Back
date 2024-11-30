package com.j2ns.backend.repositories.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.j2ns.backend.models.auth.TrajetoModel;

public interface TrajetoRepository extends JpaRepository<TrajetoModel, Long>{

}
