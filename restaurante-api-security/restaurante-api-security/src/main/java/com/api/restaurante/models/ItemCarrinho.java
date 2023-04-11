package com.api.restaurante.models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "itens_carrinho")

public class ItemCarrinho {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
    
    private ComidaModel comida;
    private BebidaModel bebida;
    private SobremesaModel sobremesa;
    private Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;

    public ItemCarrinho() {
    }

    public ItemCarrinho(ComidaModel comida, Carrinho carrinho) {
        this.comida = comida;
        this.carrinho = carrinho;
    }

    public ItemCarrinho(ComidaModel comida, BebidaModel bebida, Carrinho carrinho) {
        this.comida = comida;
        this.bebida = bebida;
        this.carrinho = carrinho;
    }

    public ItemCarrinho(ComidaModel comida, SobremesaModel sobremesa, Carrinho carrinho) {
        this.comida = comida;
        this.sobremesa = sobremesa;
        this.carrinho = carrinho;
    }

    public ItemCarrinho(ComidaModel comida, BebidaModel bebida, SobremesaModel sobremesa, Carrinho carrinho) {
        this.comida = comida;
        this.bebida = bebida;
        this.sobremesa = sobremesa;
        this.carrinho = carrinho;
    }

    public ItemCarrinho(BebidaModel bebida, Carrinho carrinho) {
        this.bebida = bebida;
        this.carrinho = carrinho;
    }

    public ItemCarrinho(BebidaModel bebida, SobremesaModel sobremesa, Carrinho carrinho) {
        this.bebida = bebida;
        this.sobremesa = sobremesa;
        this.carrinho = carrinho;
    }

    public ItemCarrinho(SobremesaModel sobremesa, Carrinho carrinho) {
        this.sobremesa = sobremesa;
        this.carrinho = carrinho;
    }
    public ComidaModel getComida() {
        return comida;
    }

    public void setComida(ComidaModel comida) {
        this.comida = comida;
    }

    public BebidaModel getBebida() {
        return bebida;
    }

    public void setBebida(BebidaModel bebida) {
        this.bebida = bebida;
    }

    public SobremesaModel getSobremesa() {
        return sobremesa;
    }

    public void setSobremesa(SobremesaModel sobremesa) {
        this.sobremesa = sobremesa;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoTotal() {
        double precoComida = this.comida != null ? this.comida.getPreco() : 0;
        double precoBebida = this.bebida != null ? this.bebida.getPreco() : 0;
        double precoSobremesa = this.sobremesa != null ? this.sobremesa.getPreco() : 0;
        return (precoComida + precoBebida + precoSobremesa) * this.quantidade;
    }


    
}