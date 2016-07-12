package br.ufsc.visualizao;

import javax.swing.JFrame;

public class JanelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	public JanelaPrincipal() {
		this.add(new Painel());
		this.setSize(500, 500);
		this.setTitle("Visualização Path");
		this.setVisible(true);
	}
}
