package br.ufsc.visualizao;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class ToolTipDoVertice implements MouseListener {

	private JButton vertice1;

	public ToolTipDoVertice(JButton vertice1) {
		this.vertice1 = vertice1;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 * Retorna como ToolTipText o valor do toolTip do retangulo.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		vertice1.setToolTipText(((Retangulo) vertice1).getToolTip());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
