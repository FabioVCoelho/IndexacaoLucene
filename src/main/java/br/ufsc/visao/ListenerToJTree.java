package br.ufsc.visao;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

import br.ufsc.JTree.VisualizacaoJTree;

public class ListenerToJTree implements MouseListener {

	private JTextField caixaDeTexto;

	public ListenerToJTree(JTextField caixaDeTexto) {
		this.caixaDeTexto = caixaDeTexto;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		new JanelaDoResultado(new VisualizacaoJTree().pesquisar(caixaDeTexto.getText()));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
