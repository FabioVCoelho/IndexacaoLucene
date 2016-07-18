package br.ufsc.treeJgraphT;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.apache.lucene.queryparser.classic.ParseException;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedMultigraph;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;

import br.ufsc.IndexacaoLucene.SearcherComFiltroDeDocumentos;
import br.ufsc.visualizao.SepararString;

public class JGraphAdapterDemo extends JApplet {
	private static final long serialVersionUID = 3256444702936019250L;
	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(600, 500);

	private JGraphModelAdapter<String, Path> jgAdapter;
	private List<String> camposRetornaveis;
	private static JFrame frame;

	/**
	 * An alternative starting point for this demo, to also allow running this
	 * applet as an application.
	 *
	 * @param args
	 *            ignored.
	 */
	public static void main(String[] args) {
		JGraphAdapterDemo applet = new JGraphAdapterDemo();

		frame = new JFrame();
		applet.init();
		frame.getContentPane().add(applet);
		frame.setTitle("JGraphT Adapter to JGraph Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void init() {
		// create a JGraphT graph
		ListenableGraph<String, Path> g = new ListenableDirectedMultigraph<>(Path.class);
		// create a visualization using JGraph, via an adapter
		jgAdapter = new JGraphModelAdapter<>(g);
		JGraph jgraph = new JGraph(jgAdapter);
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

		// Adiciona um MouseListener no JGraph para mostrar todos os caminhos
		// quando clicar no Retangulo.
		jgraph.addMouseListener(new OuvinteDoMouse(jgraph, jgAdapter));

		/*
		 * Para todos os caminhos encontrados com o SepararString, é criado um
		 * edge(ligação entre vértices) do caminho atual para o próximo, caso o
		 * próximo caminho seja o CURRICULO-VITAE, é adicionado 1 ao i, para que
		 * comece o caminho novamente pelo CURRICULO-VITAE, não havendo assim
		 * erros de edges. Os positionVertexAt são para posicionar os Vertices
		 * nas coordenadas x,y.
		 */
		for (String vertex : g.vertexSet()) {
			jgAdapter.getVertexCell(vertex).getAttributes().put("path", "");
		}
		for (int i = 0; i < pathsSeparados.size() - 1; i++) {
			if (!g.containsEdge(pathsSeparados.get(i), pathsSeparados.get(i + 1))
					&& !pathsSeparados.get(i + 1).equals("CURRICULO-VITAE")) {
				/*
				 * O vertex possui um HashMap e para guardar todos os caminhos
				 * que o vertice seguiu criei um "path" no Map e guardei os
				 * caminhos.
				 */
				AttributeMap atributosDoVertice = jgAdapter.getVertexCell(pathsSeparados.get(i)).getAttributes();
				atributosDoVertice.put("path", atributosDoVertice.get("path") + pathsSeparados.get(i) + "->"
						+ pathsSeparados.get(i + 1) + "  ");
				g.addEdge(pathsSeparados.get(i), pathsSeparados.get(i + 1));
			}
		}
		final JGraphFacade jgf = new JGraphFacade(jgraph);
		final JGraphHierarchicalLayout layoutifier = new JGraphHierarchicalLayout();
		layoutifier.run(jgf);
		System.out.println("Layout complete");

		final Map<?, ?> nestedMap = jgf.createNestedMap(true, true);
		jgraph.getGraphLayoutCache().edit(nestedMap);

		jgraph.getGraphLayoutCache().update();
		jgraph.refresh();
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