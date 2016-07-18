package br.ufsc.treeJgraphT;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JanelaPrincipal extends JApplet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JScrollPane scrollpane = new JScrollPane(panel);

		this.getContentPane().add(scrollpane, BorderLayout.CENTER);

		frame.add(this);
		panel.add(new Visualizador().jgraph());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		panel.setVisible(true);
		scrollpane.setVisible(true);
	}
}
