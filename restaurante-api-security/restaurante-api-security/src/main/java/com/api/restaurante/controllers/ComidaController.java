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

import com.api.restaurante.models.ComidaModel;
import com.api.restaurante.repositories.ComidaRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/restaurante/comida")
public class ComidaController {

	final ComidaRepository parkingSpotService;

	public ComidaController(ComidaRepository parkingSpotService) {
		this.parkingSpotService = parkingSpotService;
	}

	@PostMapping
	public ResponseEntity<Object> saveComida(ComidaModel comida) {
		if (parkingSpotService.existsByNome(comida.getNome())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ja existe essa comida");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(comida));
	}

	@GetMapping
	public ResponseEntity<Page<ComidaModel>> getAllComidas(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneComida(@PathVariable(value = "id") Long id) {
		Optional<ComidaModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteComida(@PathVariable(value = "id") Long id) {
		Optional<ComidaModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
		}
		parkingSpotService.delete(parkingSpotModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateComida(@PathVariable(value = "id") Long id,
			@RequestBody @Valid ComidaModel comida) {
		Optional<ComidaModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(comida));
	}

}
