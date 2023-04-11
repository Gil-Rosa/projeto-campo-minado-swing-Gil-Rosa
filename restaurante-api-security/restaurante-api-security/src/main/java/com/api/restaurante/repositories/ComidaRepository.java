package com.api.restaurante.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.restaurante.models.ComidaModel;

@Repository
public interface ComidaRepository extends JpaRepository<ComidaModel, Long> {
	public boolean existsByNome(String nome);
}
