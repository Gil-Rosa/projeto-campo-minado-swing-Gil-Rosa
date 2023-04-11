package com.api.restaurante.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.restaurante.models.BebidaModel;
import com.api.restaurante.models.Carrinho;
import com.api.restaurante.models.ComidaModel;
import com.api.restaurante.models.ItemCarrinho;
import com.api.restaurante.models.SobremesaModel;
import com.api.restaurante.models.Usuario;
import com.api.restaurante.repositories.Bebidarepository;
import com.api.restaurante.repositories.CarrinhoRepository;
import com.api.restaurante.repositories.ComidaRepository;
import com.api.restaurante.repositories.SobremesaRepository;
import com.api.restaurante.repositories.UserRepository;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoControler {
	@Autowired
	UserRepository userRepository;
	@Autowired
	SobremesaRepository sobremesaRepository;
	@Autowired
	Bebidarepository bebidarepository;

	 private final ComidaRepository comidaRepository;
	    private final CarrinhoRepository carrinhoRepository;

	    public CarrinhoControler(ComidaRepository comidaRepository, CarrinhoRepository carrinhoRepository) {
	        this.comidaRepository = comidaRepository;
	        this.carrinhoRepository = carrinhoRepository;
	    }
        @PostMapping
        public ResponseEntity<Carrinho> criarCompra(@RequestParam Long comidaId, @RequestParam Long bebidaId, @RequestParam Long sobremesaId, @RequestParam Map<String, Integer> quantidades,  @AuthenticationPrincipal UserDetails userDetails) {
            ComidaModel comida = comidaRepository.findById(comidaId)
                    .orElseThrow(() -> new RuntimeException("Comida não encontrada"));
            Optional<SobremesaModel> sobremesa = Optional.ofNullable(sobremesaRepository.findById(sobremesaId).orElse(null));
            Optional<BebidaModel> bebida = Optional.ofNullable(bebidarepository.findById(bebidaId).orElse(null));

            Carrinho carrinho = new Carrinho();
            List<ItemCarrinho> itensCarrinho = new ArrayList<>();
            if (comida != null) {
                ItemCarrinho itemCarrinho = new ItemCarrinho(comida, carrinho);
                int quantidade = quantidades.getOrDefault("comida", 1);
                carrinho.adicionarItem(itemCarrinho, quantidade);
                itensCarrinho.add(itemCarrinho);
            }
            if (bebida.isPresent()) {
                ItemCarrinho itemCarrinho = new ItemCarrinho(bebida.get(), carrinho);
                int quantidade = quantidades.getOrDefault("bebida", 1);
                carrinho.adicionarItem(itemCarrinho, quantidade);
                itensCarrinho.add(itemCarrinho);
            }
            if (sobremesa.isPresent()) {
                ItemCarrinho itemCarrinho = new ItemCarrinho(sobremesa.get(), carrinho);
                int quantidade = quantidades.getOrDefault("sobremesa", 1);
                carrinho.adicionarItem(itemCarrinho, quantidade);
                itensCarrinho.add(itemCarrinho);
            }
            carrinho.setPrecoTotal(itensCarrinho.stream().mapToDouble(ItemCarrinho::getPrecoTotal).sum());

            String username = userDetails.getUsername();
            Usuario user = userRepository.findBynome(username)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            carrinho.setUser(user);
            Carrinho carrinhoSalvo = carrinhoRepository.save(carrinho);
            return ResponseEntity.ok(carrinhoSalvo);
        }
	    @GetMapping
	    public ResponseEntity<List<ItemCarrinho>> getItensCarrinho() {
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	String username = auth.getPrincipal().toString();
	        Carrinho carrinho = carrinhoRepository.findByUsername(username);
	        List<ItemCarrinho> itensCarrinho = carrinho.getItens();
	        return ResponseEntity.ok(itensCarrinho);
	    }
	
}