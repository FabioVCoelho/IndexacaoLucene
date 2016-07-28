package br.ufsc.JTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

import org.apache.lucene.queryparser.classic.ParseException;

import br.ufsc.IndexacaoLucene.SearcherComFiltroDeDocumentos;

public class VisualizacaoJTree extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTree tree;

	public VisualizacaoJTree() {
		Folder root = new Folder("CURRICULO-VITAE");

		List<String> camposRetornaveis = new ArrayList<String>();
		try {
			camposRetornaveis = new SearcherComFiltroDeDocumentos().pesquisar("Carina");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		for (String fullPath : camposRetornaveis) {
			String[] paths = fullPath.split("/");
			if (paths.length >= 2) {
				Folder tempRoot = root;
				for (int i = 1; i < paths.length; i++) {
					if (i < paths.length - 1) {
						tempRoot.setFilho(paths[i]);
						tempRoot = tempRoot.getFilho(paths[i]);
					} else {
						tempRoot.add(new Folder(paths[i]));
					}
				}
			}
		}

		tree = new JTree(root);
		JPanel panel = new JPanel();
		JScrollPane scrollpane = new JScrollPane(panel);
		this.add(scrollpane);
		panel.add(tree);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		setVisible(true);
		panel.setVisible(true);
		scrollpane.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new VisualizacaoJTree();
			}
		});
	}
}