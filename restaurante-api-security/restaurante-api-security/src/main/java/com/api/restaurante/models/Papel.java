package com.api.restaurante.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Table(name ="papel")
public class Papel  {
 
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private String nome;
	
	@ManyToMany(mappedBy = "papeis", fetch = FetchType.EAGER)
	private List<Usuario> usuarios;
	
	public Papel() {}

	public Papel(String papel) {
		super();
		this.nome = papel;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getPapel() {
		return nome;
	}

	public void setPapel(String papel) {
		this.nome = papel;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	
	
	
}
