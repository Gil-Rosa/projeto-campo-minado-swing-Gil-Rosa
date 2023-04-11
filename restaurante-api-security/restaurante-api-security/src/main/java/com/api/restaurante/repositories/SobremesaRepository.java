package com.api.restaurante.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.restaurante.models.SobremesaModel;
@Repository
public interface SobremesaRepository extends JpaRepository<SobremesaModel,Long> {
	boolean existsByNome(String nome);
}
