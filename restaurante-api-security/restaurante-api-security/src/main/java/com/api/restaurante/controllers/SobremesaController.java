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

import com.api.restaurante.models.SobremesaModel;
import com.api.restaurante.repositories.SobremesaRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/restaurante/sobremesa")
public class SobremesaController {
	final SobremesaRepository sobremesaRepository;

	public SobremesaController(SobremesaRepository sobremesaReopository) {
		this.sobremesaRepository = sobremesaReopository;
	}

	@PostMapping
	public ResponseEntity<Object> saveSobremesa(SobremesaModel bebida) {
		if (sobremesaRepository.existsByNome(bebida.getNome())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ja existe essa sobremesa");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(sobremesaRepository.save(bebida));
	}

	@GetMapping
	public ResponseEntity<Page<SobremesaModel>> getAllSobremesa(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(sobremesaRepository.findAll(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneSobremesa(@PathVariable(value = "id") Long id) {
		Optional<SobremesaModel> parkingSpotModelOptional = sobremesaRepository.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("sobremesa not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteSobremesa(@PathVariable(value = "id") Long id) {
		Optional<SobremesaModel> parkingSpotModelOptional = sobremesaRepository.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("sobremesa  not found.");
		}
		sobremesaRepository.delete(parkingSpotModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("sobremesa deleted successfully.");
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateSobremesa(@PathVariable(value = "id") Long id,
			@RequestBody @Valid SobremesaModel comida) {
		Optional<SobremesaModel> parkingSpotModelOptional = sobremesaRepository.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("sobremesa not found.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(sobremesaRepository.save(comida));
	}
}
