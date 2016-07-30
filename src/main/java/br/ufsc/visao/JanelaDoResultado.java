package br.ufsc.visao;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JanelaDoResultado extends JApplet {

	private static final long serialVersionUID = 1L;
	private Dimension DEFAULT_SIZE = new Dimension(900, 700);

	public JanelaDoResultado(Component component) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JScrollPane scrollpane = new JScrollPane(panel);

		this.getContentPane().add(scrollpane);
		new BoxLayout(scrollpane, BoxLayout.PAGE_AXIS);

		frame.add(this);
		panel.add(component);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(DEFAULT_SIZE);
		frame.setVisible(true);
		panel.setVisible(true);
		scrollpane.setVisible(true);
	}

}
