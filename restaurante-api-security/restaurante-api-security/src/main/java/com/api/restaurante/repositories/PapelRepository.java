package com.api.restaurante.repositories;

import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;

import com.api.restaurante.models.Papel;



public interface PapelRepository extends JpaRepository<Papel, UUID> {
	
	Papel findByNome(String role);
  
}
