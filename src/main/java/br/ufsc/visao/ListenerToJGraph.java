package br.ufsc.visao;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

import br.ufsc.JGraph.VisualizacaoJGraph;

public class ListenerToJGraph implements MouseListener {


	private JTextField caixaDeTexto;

	public ListenerToJGraph(JTextField caixaDeTexto) {
		this.caixaDeTexto = caixaDeTexto;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		new JanelaDoResultado(new VisualizacaoJGraph().jgraph(caixaDeTexto.getText()));
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
