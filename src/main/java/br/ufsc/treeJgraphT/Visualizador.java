package br.ufsc.treeJgraphT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.ListenableDirectedGraph;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;

import br.ufsc.IndexacaoLucene.SearcherSemFiltroDeDocumentos;

public class Visualizador {

	private JGraphModelAdapter<String, Path> jgAdapter;
	private List<String> camposRetornaveis;

	@SuppressWarnings("unchecked")
	public JGraph jgraph(String string2) {
		// create a JGraphT graph
		ListenableGraph<String, Path> g = new ListenableDirectedGraph<>(Path.class);
		// create a visualization using JGraph, via an adapter
		jgAdapter = new JGraphModelAdapter<>(g);
		JGraph jgraph = new JGraph(jgAdapter);

		// Retorna os campos encontrados pela pesquisa realizada.
		try {
			camposRetornaveis = new SearcherSemFiltroDeDocumentos().pesquisar(string2);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> pathsSeparados = new ArrayList<String>();
		pathsSeparados.addAll(new SepararString().camposDaPesquisa(camposRetornaveis));
		// Adiciona cada pedaço do caminho como um retangulo no frame.
		for (String string : pathsSeparados) {
			g.addVertex(string);
			jgAdapter.getVertexCell(string).getAttributes().put("path", "");
		}

		// Adiciona um MouseListener no JGraph para mostrar todos os caminhos
		// quando clicar no Retangulo.
		jgraph.addMouseListener(new OuvinteDoMouse(jgraph, jgAdapter));

		/*
		 * Para todos os caminhos encontrados com o SepararString, é criado um
		 * edge(ligação entre vértices) do caminho atual para o próximo, caso o
		 * próximo caminho seja o CURRICULO-VITAE não é feito nada, pois
		 * constaria em uma conexão errada.
		 */
		/*
		 * O vertex possui um HashMap e para guardar todos os caminhos que o
		 * vertice seguiu criei um "path" no Map e guardei os caminhos.
		 */
		for (int i = 0; i < pathsSeparados.size() - 1; i++) {
			if (!g.containsEdge(pathsSeparados.get(i), pathsSeparados.get(i + 1))
					&& !pathsSeparados.get(i + 1).equals("CURRICULO-VITAE")) {
				AttributeMap atributosDoVertice = jgAdapter.getVertexCell(pathsSeparados.get(i)).getAttributes();
				atributosDoVertice.put("path", atributosDoVertice.get("path") + pathsSeparados.get(i) + "->"
						+ pathsSeparados.get(i + 1) + "  ");
				g.addEdge(pathsSeparados.get(i), pathsSeparados.get(i + 1));
			}
		}
		final JGraphFacade jgf = new JGraphFacade(jgraph);
		final JGraphHierarchicalLayout layoutifier = new JGraphHierarchicalLayout(true);
		layoutifier.run(jgf);

		final Map<?, ?> nestedMap = jgf.createNestedMap(true, true);
		jgraph.getGraphLayoutCache().edit(nestedMap);

		jgraph.getGraphLayoutCache().update();
		jgraph.refresh();
		return jgraph;
	}
}
