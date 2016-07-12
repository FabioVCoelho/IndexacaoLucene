package br.ufsc.visualizao;

import java.awt.Color;

import javax.swing.JButton;

public class Retangulo extends JButton {

	private static final long serialVersionUID = 1L;
	private String string;
	private String toolTip; // Utilizado para guardar os caminhos conectados.

	public Retangulo(String string, int x, int y, int width, int height) {
		this.string = string;
		this.setBackground(Color.ORANGE);
		this.setForeground(Color.BLACK);
		this.setText(string);
		this.setLocation(x, y);
		this.setSize(width, height);
		this.setEnabled(false);
		toolTip = "";
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.Component#toString()
	 * Retorna o string passado como parametro.
	 */
	@Override
	public String toString() {
		return string;
	}

	/*
	 * Utilizado para adicionar diferentes caminho para exibir no ToolTip
	 * Utilizado html para pular linha.
	 */
	public void setToolTip(String string) {
		if (!toolTip.contains(string)) {
			toolTip += string + "<br>";
		}
	}

	/*
	 * Retorna o tooltip entre <html></html> para o <br> funcione e pule uma
	 * linha
	 */
	public String getToolTip() {
		return "<html>" + toolTip + "</html>";
	}

}
