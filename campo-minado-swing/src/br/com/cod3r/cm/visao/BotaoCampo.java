package br.com.cod3r.cm.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.cod3r.cm.modelo.Campo;
import br.com.cod3r.cm.modelo.CampoEvento;
import br.com.cod3r.cm.modelo.CampoObserver;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObserver, MouseListener {
	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_MARCAR = new Color(8, 179, 247);
	private final Color BG_EXPLODIR = new Color(189, 66, 68);
	private final Color TEXTO_VERDE = new Color(0, 100, 0);

	Campo campo;

	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
        addMouseListener(this);
		campo.addObserver(this);
	}

	public void eventoOcorreu(Campo campo, CampoEvento evento) {

		switch (evento) {
		case ABRIR:
			aplicarMetodoAbrir();
			break;
		case MARCAR:
			aplicarMetodoMarcar();
			break;
		case EXPLODIR:
			aplicarMetodoExplodir();
			break;

		default:
			aplicarEstiloPadrao();
		
		}
	}

	private void aplicarEstiloPadrao() {
		setBackground(BG_PADRAO);
        setText("");
        setBorder(BorderFactory.createBevelBorder(0));
	}

	private void aplicarMetodoExplodir() {
	
		setBackground(BG_EXPLODIR);
		setForeground(Color.WHITE);
        setText("X");

	}

	private void aplicarMetodoMarcar() {
		setBackground(BG_MARCAR);
		setForeground(Color.BLACK);
        setText("M");
	}

	private void aplicarMetodoAbrir() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if(campo.isMinado()) {
			setBackground(BG_EXPLODIR);
			return;
		}
		setBackground(BG_PADRAO);
		
		switch (campo.minasNaVizinhaca()) {
		case 1:
			setForeground(TEXTO_VERDE);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		String valor = !campo.vizinhosSeguros() ? campo.minasNaVizinhaca() + "" :
			"";
		setText(valor);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==1) {
			campo.abrir();
		}else {
        campo.alterarMarcacao();
		}

	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
