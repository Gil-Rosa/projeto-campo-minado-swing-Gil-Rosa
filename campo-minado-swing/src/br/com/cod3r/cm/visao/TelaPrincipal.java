package br.com.cod3r.cm.visao;

import javax.swing.JFrame;

import br.com.cod3r.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame{
	
	public TelaPrincipal() {
		Tabuleiro tabuleiro = new Tabuleiro(16, 30, 5);
		
		add(new PainelTabuleiro(tabuleiro));
		setTitle("campo Minado");
		setSize(690,438);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		new TelaPrincipal();
	}

}
