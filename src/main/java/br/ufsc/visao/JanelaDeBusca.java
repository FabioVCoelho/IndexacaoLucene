package br.ufsc.visao;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JanelaDeBusca extends JApplet {

	private static final long serialVersionUID = 1L;

	/*
	 * Janela de busca criada para que o usuario possa inserir o termo que está
	 * procurando. É utilizado um botão que busca o texto inserido no JTextField
	 * e passa para o init.
	 */
	public JanelaDeBusca() {
		JFrame frame = new JFrame("Termo da pesquisa");
		JPanel panel = new JPanel();
		frame.add(panel);
		final JTextField caixaDeTexto = new JTextField("Digite o termo da Pesquisa");
		panel.add(caixaDeTexto);
		JButton botaoJGraph = new JButton("Visualização JGraph");
		panel.add(botaoJGraph);
		botaoJGraph.addMouseListener(new ListenerToJGraph(caixaDeTexto));
		JButton botaoJTree = new JButton("Visualização JTree");
		panel.add(botaoJTree);
		botaoJTree.addMouseListener(new ListenerToJTree(caixaDeTexto));
		frame.setVisible(true);
		panel.setVisible(true);
		frame.pack();
	}
}
