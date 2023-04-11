package com.api.restaurante.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "carrinhos")
public class Carrinho {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @OneToMany(mappedBy = "carrinho")
	    private List<ItemCarrinho> itens = new ArrayList<>();

	    private double precoTotal;

	    private String username;
	    @ManyToOne
	    @JoinColumn(name = "usuario_id")
      private Usuario user ;
	    public void adicionarItem(ItemCarrinho item) {
	        this.itens.add(item);
	    }

	    public Usuario getUser() {
			return user;
		}

		public void setUser(Usuario user) {
			this.user = user;
		}

		public void removerItem(ItemCarrinho item) {
	        this.itens.remove(item);
	    }

	    public List<ItemCarrinho> getItens() {
	        return this.itens;
	    }

	    public void setPrecoTotal(double precoTotal) {
	        this.precoTotal = precoTotal;
	    }

	    public double getPrecoTotal() {
	        return this.precoTotal;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }
	    public void adicionarItem(ItemCarrinho item, Integer quantidade) {
	        item.setQuantidade(quantidade);
	        this.itens.add(item);
	    }
	}

