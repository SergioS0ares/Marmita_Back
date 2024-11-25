package com.fachter.backend.repositories;


import com.fachter.backend.entities.Cliente;
import com.fachter.backend.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
