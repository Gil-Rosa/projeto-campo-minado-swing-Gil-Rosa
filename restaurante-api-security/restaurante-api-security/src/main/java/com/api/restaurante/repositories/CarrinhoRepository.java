package com.api.restaurante.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.restaurante.models.Carrinho;
@Repository
public interface CarrinhoRepository  extends JpaRepository<Carrinho, Long> {
	Carrinho findByUsername(String username);
}
