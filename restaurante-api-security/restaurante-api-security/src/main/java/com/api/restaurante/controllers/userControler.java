package com.api.restaurante.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.restaurante.models.Papel;
import com.api.restaurante.models.Usuario;
import com.api.restaurante.repositories.PapelRepository;
import com.api.restaurante.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping
public class userControler {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PapelRepository papelRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	 
	@PostMapping("/cadastrar/register")
	public ResponseEntity<String> registerUser(@Valid Usuario user) {
		if (userRepository.findBynome(user.getNome()) != null) {
			return ResponseEntity.badRequest().body("Username already exists");
		}
		if (userRepository.findByEmail(user.getEmail()) != null) {
			return ResponseEntity.badRequest().body("Email already exists");
		}
		if (userRepository.findByLogin(user.getLogin()) != null) {
			return ResponseEntity.badRequest().body("login already exists");
		}
		List<Papel> papeis = new ArrayList<Papel>();
		
		if (!userRepository.existsByPapeisNome("ADMIN")) {
			
			Papel papel = papelRepository.findByNome("ADMIN");
			papeis.add(papel);
			user.setPapeis(papeis);
		} else  {
			Papel papel = papelRepository.findByNome("USER");
			
			papeis.add(papel);
			user.setPapeis(papeis);
		}

		user.setPassword(passwordEncoder.encode(user.getPassword())); 
		userRepository.save(user);
		return ResponseEntity.ok("User registered successfully");
	}
	@GetMapping("/usuarios")
	public ResponseEntity<Page<Usuario>> getAllParkingSpots(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll(pageable));
	}

	@PutMapping("/usuarios/{id}")
	public ResponseEntity<String> updateUser(@PathVariable UUID id, @Valid Usuario user) {
	Usuario existingUser = userRepository.findById(id).orElse(null);
	if (existingUser == null) {
	return ResponseEntity.notFound().build();
	}
	if (!existingUser.getEmail().equals(user.getEmail()) && userRepository.findByEmail(user.getEmail()) != null) {
	return ResponseEntity.badRequest().body("Email already exists");
	}
	if (!existingUser.getLogin().equals(user.getLogin()) && userRepository.findByLogin(user.getLogin()) != null) {
	return ResponseEntity.badRequest().body("login already exists");
	}
	existingUser.setNome(user.getNome());
	existingUser.setEmail(user.getEmail());
	existingUser.setLogin(user.getLogin());
	existingUser.setPassword(passwordEncoder.encode(user.getPassword())); 
	userRepository.save(existingUser);
	return ResponseEntity.ok("User updated successfully");
	}

	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
	Usuario existingUser = userRepository.findById(id).orElse(null);
	if (existingUser == null) {
	return ResponseEntity.notFound().build();
	}
	userRepository.delete(existingUser);
	return ResponseEntity.ok("User deleted successfully");
	}
}
