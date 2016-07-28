package br.ufsc.JTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.lucene.queryparser.classic.ParseException;

import br.ufsc.IndexacaoLucene.SearcherComFiltroDeDocumentos;
import br.ufsc.treeJgraphT.SepararString;

public class Demo extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree tree;
	private int contador = 0;

	public Demo() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("CURRICULO-VITAE");

		List<String> camposRetornaveis = new ArrayList<String>();
		try {
			camposRetornaveis = new SearcherComFiltroDeDocumentos().pesquisar("Carina");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		List<String> pathsSeparados = new ArrayList<String>();
		pathsSeparados.addAll(new SepararString().camposDaPesquisa(camposRetornaveis));

		for (int i = 1; i < 500;) {
			if (pathsSeparados.get(i).equals(pathsSeparados.get(0))) {
				contador = 0;
				i++;
			}
			while (!pathsSeparados.get(i).equals(pathsSeparados.get(0))) {
				contador++;
				i++;
			}
			if (contador == 2) {
				DefaultMutableTreeNode nodo1 = new DefaultMutableTreeNode(pathsSeparados.get(i - 2));
				root.add(nodo1);
				nodo1.add(new DefaultMutableTreeNode(pathsSeparados.get(i - 1)));
			}
			if (contador == 3) {
				DefaultMutableTreeNode nodo1 = new DefaultMutableTreeNode(pathsSeparados.get(i - 3));
				DefaultMutableTreeNode nodo2 = new DefaultMutableTreeNode(pathsSeparados.get(i - 2));
				root.add(nodo1);
				nodo1.add(nodo2);
				nodo2.add(new DefaultMutableTreeNode(pathsSeparados.get(i - 1)));
			}
			if (contador == 4) {
				DefaultMutableTreeNode nodo1 = new DefaultMutableTreeNode(pathsSeparados.get(i - 4));
				DefaultMutableTreeNode nodo2 = new DefaultMutableTreeNode(pathsSeparados.get(i - 3));
				DefaultMutableTreeNode nodo3 = new DefaultMutableTreeNode(pathsSeparados.get(i - 2));
				DefaultMutableTreeNode nodo4 = new DefaultMutableTreeNode(pathsSeparados.get(i - 1));
				root.add(nodo1);
				nodo1.add(nodo2);
				nodo2.add(nodo3);
				nodo3.add(nodo4);
			}
			if (contador == 5) {
				DefaultMutableTreeNode nodo1 = new DefaultMutableTreeNode(pathsSeparados.get(i - 5));
				DefaultMutableTreeNode nodo2 = new DefaultMutableTreeNode(pathsSeparados.get(i - 4));
				DefaultMutableTreeNode nodo3 = new DefaultMutableTreeNode(pathsSeparados.get(i - 3));
				DefaultMutableTreeNode nodo4 = new DefaultMutableTreeNode(pathsSeparados.get(i - 2));
				DefaultMutableTreeNode nodo5 = new DefaultMutableTreeNode(pathsSeparados.get(i - 1));
				root.add(nodo1);
				nodo1.add(nodo2);
				nodo2.add(nodo3);
				nodo3.add(nodo4);
				nodo4.add(nodo5);
			}
			if (contador == 6) {
				DefaultMutableTreeNode nodo1 = new DefaultMutableTreeNode(pathsSeparados.get(i - 6));
				DefaultMutableTreeNode nodo2 = new DefaultMutableTreeNode(pathsSeparados.get(i - 5));
				DefaultMutableTreeNode nodo3 = new DefaultMutableTreeNode(pathsSeparados.get(i - 4));
				DefaultMutableTreeNode nodo4 = new DefaultMutableTreeNode(pathsSeparados.get(i - 3));
				DefaultMutableTreeNode nodo5 = new DefaultMutableTreeNode(pathsSeparados.get(i - 2));
				DefaultMutableTreeNode nodo6 = new DefaultMutableTreeNode(pathsSeparados.get(i - 1));
				root.add(nodo1);
				nodo1.add(nodo2);
				nodo2.add(nodo3);
				nodo3.add(nodo4);
				nodo4.add(nodo5);
				nodo5.add(nodo6);
			}
			if (contador == 7) {
				DefaultMutableTreeNode nodo1 = new DefaultMutableTreeNode(pathsSeparados.get(i - 7));
				DefaultMutableTreeNode nodo2 = new DefaultMutableTreeNode(pathsSeparados.get(i - 6));
				DefaultMutableTreeNode nodo3 = new DefaultMutableTreeNode(pathsSeparados.get(i - 5));
				DefaultMutableTreeNode nodo4 = new DefaultMutableTreeNode(pathsSeparados.get(i - 4));
				DefaultMutableTreeNode nodo5 = new DefaultMutableTreeNode(pathsSeparados.get(i - 3));
				DefaultMutableTreeNode nodo6 = new DefaultMutableTreeNode(pathsSeparados.get(i - 2));
				DefaultMutableTreeNode nodo7 = new DefaultMutableTreeNode(pathsSeparados.get(i - 1));
				root.add(nodo1);
				nodo1.add(nodo2);
				nodo2.add(nodo3);
				nodo3.add(nodo4);
				nodo4.add(nodo5);
				nodo5.add(nodo6);
				nodo6.add(nodo7);
			}
			if (contador == 8) {
				DefaultMutableTreeNode nodo1 = new DefaultMutableTreeNode(pathsSeparados.get(i - 8));
				DefaultMutableTreeNode nodo2 = new DefaultMutableTreeNode(pathsSeparados.get(i - 7));
				DefaultMutableTreeNode nodo3 = new DefaultMutableTreeNode(pathsSeparados.get(i - 6));
				DefaultMutableTreeNode nodo4 = new DefaultMutableTreeNode(pathsSeparados.get(i - 5));
				DefaultMutableTreeNode nodo5 = new DefaultMutableTreeNode(pathsSeparados.get(i - 4));
				DefaultMutableTreeNode nodo6 = new DefaultMutableTreeNode(pathsSeparados.get(i - 3));
				DefaultMutableTreeNode nodo7 = new DefaultMutableTreeNode(pathsSeparados.get(i - 2));
				DefaultMutableTreeNode nodo8 = new DefaultMutableTreeNode(pathsSeparados.get(i - 1));
				root.add(nodo1);
				nodo1.add(nodo2);
				nodo2.add(nodo3);
				nodo3.add(nodo4);
				nodo4.add(nodo5);
				nodo5.add(nodo6);
				nodo6.add(nodo7);
				nodo7.add(nodo8);
			}
			if (contador == 9) {
				DefaultMutableTreeNode nodo1 = new DefaultMutableTreeNode(pathsSeparados.get(i - 9));
				DefaultMutableTreeNode nodo2 = new DefaultMutableTreeNode(pathsSeparados.get(i - 8));
				DefaultMutableTreeNode nodo3 = new DefaultMutableTreeNode(pathsSeparados.get(i - 7));
				DefaultMutableTreeNode nodo4 = new DefaultMutableTreeNode(pathsSeparados.get(i - 6));
				DefaultMutableTreeNode nodo5 = new DefaultMutableTreeNode(pathsSeparados.get(i - 5));
				DefaultMutableTreeNode nodo6 = new DefaultMutableTreeNode(pathsSeparados.get(i - 4));
				DefaultMutableTreeNode nodo7 = new DefaultMutableTreeNode(pathsSeparados.get(i - 3));
				DefaultMutableTreeNode nodo8 = new DefaultMutableTreeNode(pathsSeparados.get(i - 2));
				DefaultMutableTreeNode nodo9 = new DefaultMutableTreeNode(pathsSeparados.get(i - 1));
				root.add(nodo1);
				nodo1.add(nodo2);
				nodo2.add(nodo3);
				nodo3.add(nodo4);
				nodo4.add(nodo5);
				nodo5.add(nodo6);
				nodo6.add(nodo7);
				nodo7.add(nodo8);
				nodo8.add(nodo9);
			}
		}

		tree = new JTree(root);
		add(tree);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("JTree Example");
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Demo();
			}
		});
	}
}