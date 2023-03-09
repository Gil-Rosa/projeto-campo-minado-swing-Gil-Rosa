package br.com.cod3r.cm.modelo;

import java.util.ArrayList;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObserver {

	private final int linhas;
	private final int colunas;
	private final int minas;
	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<Resultado>> observadores = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;

		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}

	public void paraCada(Consumer<Campo> funcao) {
		campos.stream().forEach(funcao);
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void registrarObservador(Consumer<Resultado> observador) {
		observadores.add(observador);
	}

	private void notificarobservadores(boolean resultado) {
		observadores.stream().forEach(o -> o.accept(new Resultado(resultado)));
	}

	public void abrir(int linha, int coluna) {
		campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.abrir());

	}

	private void mostraMinas() {
		campos.stream().filter(e -> e.isMinado() && !e.isMarcado())
		
		.forEach(c -> c.setAberto(true));
	}

	public void alterarMarcacao(int linha, int coluna) {
		campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.alterarMarcacao());
	}

	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		do {
			minasArmadas = campos.stream().filter(minado).count();
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		} while (minasArmadas < minas);
	}

	private void associarVizinhos() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}

	}

	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				Campo campo = new Campo(linha, coluna);
				campo.addObserver(this);
				campos.add(campo);
			}
		}
	}

	public boolean objetivoAlcancado() {
		return campos.stream()
				
				.allMatch(c -> c.objetivoAlcancado());
	}

	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
		
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {

		if (evento == CampoEvento.EXPLODIR) {
			mostraMinas();
			notificarobservadores(false);
			
		} else if (objetivoAlcancado()) {

			notificarobservadores(true);

		}
	}
}
