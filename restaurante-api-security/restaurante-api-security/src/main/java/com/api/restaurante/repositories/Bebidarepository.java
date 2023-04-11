package com.api.restaurante.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.restaurante.models.BebidaModel;

@Repository
public interface Bebidarepository extends JpaRepository<BebidaModel, Long> {
	boolean existsByNome(String nome);
}
