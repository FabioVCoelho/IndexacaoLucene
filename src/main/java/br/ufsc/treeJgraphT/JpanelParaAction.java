package br.ufsc.treeJgraphT;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;

public class JpanelParaAction extends JButton {

	private static final long serialVersionUID = 1L;
	private Rectangle2D retangulo;

	/*
	 * Método do JBUtton para criar um mouseListener que mostra qual o valor do
	 * vértice que o mouse está (achar método para pegar os filhos do mesmo)
	 * Object.toString() = Valor do Vértice.
	 */
	public JpanelParaAction(Object object, int x, int y, Rectangle2D bounds) {
		this.addMouseListener(new OuvinteDoMouse(object.toString()));
		this.setBackground(Color.ORANGE);
		this.setForeground(Color.BLACK);
		this.setText(object.toString());
		this.setLocation(x, y);
		this.setSize((int) bounds.getWidth(), (int) bounds.getHeight());
		this.setEnabled(false);
		this.setToolTipText(object.toString());
		retangulo = new Rectangle2D.Double(x, y, bounds.getWidth(), bounds.getHeight());
	}

	public Rectangle2D retornaRetangulo() {
		return retangulo;
	}
}
