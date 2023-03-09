package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {
	private final int linha;
	private final int coluna;

	private boolean aberto;
	private boolean minado;
	private boolean marcado;

	List<Campo> vizinhos = new ArrayList<>();
	List<CampoObserver> observadores = new ArrayList<>();

	public Campo(int linha, int coluna) {
		
		this.linha = linha;
		this.coluna = coluna;
	}

	public void addObserver(CampoObserver observador) {
		observadores.add(observador);
	}

	private void notificarobservadores(CampoEvento evento) {
		observadores.stream().forEach(o -> o.eventoOcorreu(this, evento));
	}

	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltageral = deltaColuna + deltaLinha;

		if (deltageral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;

		} else if (deltageral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}

	public void alterarMarcacao() {
		
		if (!aberto) {
			marcado = !marcado;
			if (marcado) {
				notificarobservadores(CampoEvento.MARCAR);

			} else {
				notificarobservadores(CampoEvento.DESMARCAR);

			}
		}
		
	}

	public boolean abrir() {
		

		if (!aberto && !marcado) {
			if (minado  ){
				notificarobservadores(CampoEvento.EXPLODIR);
				return true;
				}
		setAberto(true);
			if (vizinhosSeguros()) {
				vizinhos.forEach(v -> v.abrir());
			}

			return true;

		}

		else {
			return false;
		}

	}

	public boolean vizinhosSeguros() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}

	void minar() {
		minado = true;
	}

	public boolean isFechado() {
		return !isAberto();
	}

	public boolean isAberto() {
		return aberto;
	}

	void setAberto(boolean aberto) {
		this.aberto = aberto;
		if (aberto) {
			notificarobservadores(CampoEvento.ABRIR);
		}
	}

	public boolean isMarcado() {
		return marcado;
	}

	public boolean isMinado() {
		return minado;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !minado  && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}

	public int minasNaVizinhaca() {
		return (int) vizinhos.stream().filter(v -> v.minado).count();
	}

	void reiniciar() {
		aberto = false;
		marcado = false;
		minado = false;
		notificarobservadores(CampoEvento.REINICIAR);
	}

}
