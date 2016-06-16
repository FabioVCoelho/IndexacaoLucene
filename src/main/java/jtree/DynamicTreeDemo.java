package jtree;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.lucene.queryparser.classic.ParseException;

import br.ufsc.IndexacaoLucene.SearcherComFiltroDeDocumentos;
import br.ufsc.treeJgraphT.SepararString;

public class DynamicTreeDemo extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int newNodeSuffix = 1;
	private static String ADD_COMMAND = "add";
	private static String REMOVE_COMMAND = "remove";
	private static String CLEAR_COMMAND = "clear";

	private DynamicTree treePanel;
	private int contador = 0;

	public DynamicTreeDemo() {
		super(new BorderLayout());

		// Create the components.
		treePanel = new DynamicTree();
		populateTree(treePanel);

		JButton addButton = new JButton("Add");
		addButton.setActionCommand(ADD_COMMAND);
		addButton.addActionListener(this);

		JButton removeButton = new JButton("Remove");
		removeButton.setActionCommand(REMOVE_COMMAND);
		removeButton.addActionListener(this);

		JButton clearButton = new JButton("Clear");
		clearButton.setActionCommand(CLEAR_COMMAND);
		clearButton.addActionListener(this);

		// Lay everything out.
		treePanel.setPreferredSize(new Dimension(300, 150));
		add(treePanel, BorderLayout.CENTER);

		JPanel panel = new JPanel(new GridLayout(0, 3));
		panel.add(addButton);
		panel.add(removeButton);
		panel.add(clearButton);
		add(panel, BorderLayout.SOUTH);
	}

	public void populateTree(DynamicTree treePanel) {

		List<String> camposRetornaveis = new ArrayList<String>();
		try {
			camposRetornaveis = new SearcherComFiltroDeDocumentos().pesquisar();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		List<String> pathsSeparados = new ArrayList<String>();
		pathsSeparados.addAll(new SepararString().camposDaPesquisa(camposRetornaveis));
		
		for (int i = 1; i < 500;) {
			System.out.println(i);
			System.out.println(contador);
			if (pathsSeparados.get(i).equals(pathsSeparados.get(0))) {
				contador = 0;
				i++;
			}
			while (!pathsSeparados.get(i).equals(pathsSeparados.get(0))) {
				contador++;
				i++;
			}
			if (contador == 2) {
				DefaultMutableTreeNode t1 = treePanel.addObject(pathsSeparados.get(i - 2));
				treePanel.addObject(t1, pathsSeparados.get(i - 1));
			}
			if (contador == 3) {
				DefaultMutableTreeNode t1 = treePanel.addObject(pathsSeparados.get(i - 3));
				DefaultMutableTreeNode t2 = treePanel.addObject(t1, pathsSeparados.get(i - 2));
				treePanel.addObject(t2, pathsSeparados.get(i - 1));
			}
			if (contador == 4) {
				DefaultMutableTreeNode t0 = treePanel.addObject(pathsSeparados.get(i - 4));
				DefaultMutableTreeNode t1 = treePanel.addObject(t0, pathsSeparados.get(i - 3));
				DefaultMutableTreeNode t2 = treePanel.addObject(t1, pathsSeparados.get(i - 2));
				treePanel.addObject(t2, pathsSeparados.get(i - 1));
			}
			if (contador == 5) {
				DefaultMutableTreeNode t0 = treePanel.addObject(pathsSeparados.get(i - 5));
				DefaultMutableTreeNode t1 = treePanel.addObject(t0, pathsSeparados.get(i - 4));
				DefaultMutableTreeNode t2 = treePanel.addObject(t1, pathsSeparados.get(i - 3));
				DefaultMutableTreeNode t3 = treePanel.addObject(t2, pathsSeparados.get(i - 2));
				treePanel.addObject(t3, pathsSeparados.get(i - 1));
			}
			if (contador == 6) {
				DefaultMutableTreeNode t0 = treePanel.addObject(pathsSeparados.get(i - 6));
				DefaultMutableTreeNode t1 = treePanel.addObject(t0, pathsSeparados.get(i - 5));
				DefaultMutableTreeNode t2 = treePanel.addObject(t1, pathsSeparados.get(i - 4));
				DefaultMutableTreeNode t3 = treePanel.addObject(t2, pathsSeparados.get(i - 3));
				DefaultMutableTreeNode t4 = treePanel.addObject(t3, pathsSeparados.get(i - 2));
				treePanel.addObject(t4, pathsSeparados.get(i - 1));
			}
			if (contador == 7) {
				DefaultMutableTreeNode t1 = treePanel.addObject(pathsSeparados.get(i - 7));
				DefaultMutableTreeNode t2 = treePanel.addObject(t1, pathsSeparados.get(i - 6));
				DefaultMutableTreeNode t3 = treePanel.addObject(t2, pathsSeparados.get(i - 5));
				DefaultMutableTreeNode t4 = treePanel.addObject(t3, pathsSeparados.get(i - 4));
				DefaultMutableTreeNode t5 = treePanel.addObject(t4, pathsSeparados.get(i - 3));
				DefaultMutableTreeNode t6 = treePanel.addObject(t5, pathsSeparados.get(i - 2));
				treePanel.addObject(t6, pathsSeparados.get(i - 1));
			}
			if (contador == 8) {
				DefaultMutableTreeNode t1 = treePanel.addObject(pathsSeparados.get(i - 8));
				DefaultMutableTreeNode t2 = treePanel.addObject(t1, pathsSeparados.get(i - 7));
				DefaultMutableTreeNode t3 = treePanel.addObject(t2, pathsSeparados.get(i - 6));
				DefaultMutableTreeNode t4 = treePanel.addObject(t3, pathsSeparados.get(i - 5));
				DefaultMutableTreeNode t5 = treePanel.addObject(t4, pathsSeparados.get(i - 4));
				DefaultMutableTreeNode t6 = treePanel.addObject(t5, pathsSeparados.get(i - 3));
				DefaultMutableTreeNode t7 = treePanel.addObject(t6, pathsSeparados.get(i - 2));
				treePanel.addObject(t7, pathsSeparados.get(i - 1));
			}
			if (contador == 9) {
				DefaultMutableTreeNode t0 = treePanel.addObject(pathsSeparados.get(i - 9));
				DefaultMutableTreeNode t1 = treePanel.addObject(t0, pathsSeparados.get(i - 8));
				DefaultMutableTreeNode t2 = treePanel.addObject(t1, pathsSeparados.get(i - 7));
				DefaultMutableTreeNode t3 = treePanel.addObject(t2, pathsSeparados.get(i - 6));
				DefaultMutableTreeNode t4 = treePanel.addObject(t3, pathsSeparados.get(i - 5));
				DefaultMutableTreeNode t5 = treePanel.addObject(t4, pathsSeparados.get(i - 4));
				DefaultMutableTreeNode t6 = treePanel.addObject(t5, pathsSeparados.get(i - 3));
				DefaultMutableTreeNode t7 = treePanel.addObject(t6, pathsSeparados.get(i - 2));
				treePanel.addObject(t7, pathsSeparados.get(i - 1));
			}

			// DefaultMutableTreeNode t1 = treePanel.addObject("Algo");
			// DefaultMutableTreeNode t2 = treePanel.addObject(t1,
			// "OutraCoisa");
			// treePanel.addObject(t2, "MaisNada");
		}
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (ADD_COMMAND.equals(command)) {
			// Add button clicked
			treePanel.addObject("New Node " + newNodeSuffix++);
		} else if (REMOVE_COMMAND.equals(command)) {
			// Remove button clicked
			treePanel.removeCurrentNode();
		} else if (CLEAR_COMMAND.equals(command)) {
			// Clear button clicked.
			treePanel.clear();
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("DynamicTreeDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		DynamicTreeDemo newContentPane = new DynamicTreeDemo();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
