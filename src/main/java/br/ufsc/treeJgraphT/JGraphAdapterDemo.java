package br.ufsc.treeJgraphT;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.apache.lucene.queryparser.classic.ParseException;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
// resolve ambiguity
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedMultigraph;

import br.ufsc.IndexacaoLucene.SearcherComFiltroDeDocumentos;

public class JGraphAdapterDemo extends JApplet {
	private static final long serialVersionUID = 3256444702936019250L;
	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

	//
	private JGraphModelAdapter<String, DefaultEdge> jgAdapter;
	private List<String> camposRetornaveis;

	/**
	 * An alternative starting point for this demo, to also allow running this
	 * applet as an application.
	 *
	 * @param args
	 *            ignored.
	 */
	public static void main(String[] args) {
		JGraphAdapterDemo applet = new JGraphAdapterDemo();
		applet.init();

		JFrame frame = new JFrame();
		frame.getContentPane().add(new JScrollPane(applet));
		frame.setTitle("JGraphT Adapter to JGraph Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * {@inheritDoc}
	 */
	public void init() {
		// create a JGraphT graph
		ListenableGraph<String, DefaultEdge> g = new ListenableDirectedMultigraph<>(DefaultEdge.class);

		// create a visualization using JGraph, via an adapter
		jgAdapter = new JGraphModelAdapter<>(g);

		JGraph jgraph = new JGraph(jgAdapter);
		adjustDisplaySettings(jgraph);
		getContentPane().add(jgraph);
		resize(DEFAULT_SIZE);

		// Retorna os campos encontrados pela pesquisa realizada.
		try {
			camposRetornaveis = new SearcherComFiltroDeDocumentos().pesquisar();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		List<String> pathsSeparados = new ArrayList<String>();
		pathsSeparados.addAll(new SepararString().camposDaPesquisa(camposRetornaveis));
		// Adiciona cada pedaço do caminho como um retangulo no frame.
		for (String string : pathsSeparados) {
			g.addVertex(string);
		}
		// Ordenação dos retangulos por pixel.
		int x2 = 250;
		int y2 = 10;
		int y1 = 10;
		int x1 = 10;

		/*
		 *  Para todos os caminhos encontrados com o SepararString, é criado um edge(ligação
		 *  entre vértices) do caminho atual para o próximo, caso o próximo caminho seja o
		 *  CURRICULO-VITAE, é adicionado 1 ao i, para que comece o caminho novamente pelo
		 *  CURRICULO-VITAE, não havendo assim erros de edges.
		 *  Os positionVertexAt são para posicionar os Vertices nas coordenadas x,y.
		 */
		for (int i = 0; i < pathsSeparados.size() - 1; i++) {
			if (pathsSeparados.get(i + 1).equals("CURRICULO-VITAE"))
				i++;
			if (!g.containsEdge(pathsSeparados.get(i), pathsSeparados.get(i + 1))) {
				g.addEdge(pathsSeparados.get(i), pathsSeparados.get(i + 1));
				positionVertexAt(pathsSeparados.get(i), x1 , y1);
				positionVertexAt(pathsSeparados.get(i+1), x2 , y2);
				y1 = y1+70;
				y2 = y2+70;
				if (y1 > 600)	{
					x1 = x1 + 70;
					y1 = 10;
				}
				if (y2 > 600)	{
					x2 = x2 + 70;
					y2 = 10;
				}
			}
		}
	}

	private void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(DEFAULT_SIZE);

		Color c = DEFAULT_BG_COLOR;
		String colorStr = null;

		try {
			colorStr = getParameter("bgcolor");
		} catch (Exception e) {
		}

		if (colorStr != null) {
			c = Color.decode(colorStr);
		}

		jg.setBackground(c);
	}

	/*
	 * Método retirado da demo. Posiciona o vertice nas coordenadas passadas como x e y.
	 */
	@SuppressWarnings("unchecked") // FIXME hb 28-nov-05: See FIXME below
	private void positionVertexAt(Object vertex, int x, int y) {
		DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
		AttributeMap attr = cell.getAttributes();
		Rectangle2D bounds = GraphConstants.getBounds(attr);

		Rectangle2D newBounds = new Rectangle2D.Double(x, y, bounds.getWidth(), bounds.getHeight());

		GraphConstants.setBounds(attr, newBounds);

		// TODO: Clean up generics once JGraph goes generic
		AttributeMap cellAttr = new AttributeMap();
		cellAttr.put(cell, attr);
		jgAdapter.edit(cellAttr, null, null, null);
	}

	/**
	 * a listenable directed multigraph that allows loops and parallel edges.
	 */
	private static class ListenableDirectedMultigraph<V, E> extends DefaultListenableGraph<V, E>
			implements DirectedGraph<V, E> {
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		ListenableDirectedMultigraph(Class<E> edgeClass) {
			super((Graph<V, E>) new DirectedMultigraph<Object, E>(edgeClass));
		}
	}
}