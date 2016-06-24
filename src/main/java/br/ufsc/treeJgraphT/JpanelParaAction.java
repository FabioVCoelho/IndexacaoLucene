package br.ufsc.treeJgraphT;

import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class JpanelParaAction extends JPanel {

	private static final long serialVersionUID = 1L;
	private Rectangle2D retangulo;

	public JpanelParaAction(int x, int y, Rectangle2D bounds) {
		retangulo = new Rectangle2D.Double(x, y, bounds.getWidth(), bounds.getHeight());
		this.setLocation(x, y);
		this.setSize((int) bounds.getWidth(), (int) bounds.getHeight());
		this.addMouseListener(new OuvinteDoMouse());
		this.setVisible(true);
	}

	public Rectangle2D retornaRetangulo() {
		return retangulo;
	}

}
