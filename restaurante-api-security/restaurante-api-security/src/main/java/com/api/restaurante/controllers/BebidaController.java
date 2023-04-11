package com.api.restaurante.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.restaurante.models.BebidaModel;
import com.api.restaurante.repositories.Bebidarepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/restaurante/bebida")
public class BebidaController {
	final Bebidarepository bebidaRepository;

	public BebidaController(Bebidarepository bebidaReopository) {
		this.bebidaRepository = bebidaReopository;
	}

	@PostMapping
	public ResponseEntity<Object> saveBebida(BebidaModel bebida) {
		if (bebidaRepository.existsByNome(bebida.getNome())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ja existe essa bebida");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(bebidaRepository.save(bebida));
	}

	@GetMapping
	public ResponseEntity<Page<BebidaModel>> getAllBebida(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(bebidaRepository.findAll(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneBebida(@PathVariable(value = "id") Long id) {
		Optional<BebidaModel> parkingSpotModelOptional = bebidaRepository.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bebida not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteBebida(@PathVariable(value = "id") Long id) {
		Optional<BebidaModel> parkingSpotModelOptional = bebidaRepository.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bebida  not found.");
		}
		bebidaRepository.delete(parkingSpotModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("bebida deleted successfully.");
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateBebida(@PathVariable(value = "id") Long id,
			@RequestBody @Valid BebidaModel comida) {
		Optional<BebidaModel> parkingSpotModelOptional = bebidaRepository.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bebida not found.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(bebidaRepository.save(comida));
	}
}
