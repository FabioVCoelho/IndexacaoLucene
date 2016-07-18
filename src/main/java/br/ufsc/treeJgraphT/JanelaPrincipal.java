package br.ufsc.treeJgraphT;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class JanelaPrincipal extends JApplet {

	private static final long serialVersionUID = 1L;

	public void init(String string) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JScrollPane scrollpane = new JScrollPane(panel);

		this.getContentPane().add(scrollpane, BorderLayout.CENTER);

		frame.add(this);

		panel.add(new Visualizador().jgraph(string));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		panel.setVisible(true);
		scrollpane.setVisible(true);
	}

	public void termoDeBusca() {
		JFrame frame = new JFrame("Termo da pesquisa");
		JPanel panel = new JPanel();
		frame.add(panel);
		final JTextField txtA = new JTextField("Digite o termo da Pesquisa");
		panel.add(txtA);
		final JButton botao = new JButton("Buscar");
		panel.add(botao);
		botao.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				init(txtA.getText());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		frame.setVisible(true);
		panel.setVisible(true);
		frame.pack();
	}
}
