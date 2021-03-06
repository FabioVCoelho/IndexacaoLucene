package br.ufsc.JGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.SwingConstants;

import org.apache.lucene.queryparser.classic.ParseException;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.ListenableDirectedGraph;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;

import br.ufsc.searchers.SearcherSemFiltroDeDocumentos;

public class VisualizacaoJGraph {

	private JGraphModelAdapter<String, Path> jgAdapter;
	private List<String> camposRetornaveis;

	@SuppressWarnings("unchecked")
	public JGraph jgraph(String procurarPor) {
		// create a JGraphT graph
		ListenableGraph<String, Path> g = new ListenableDirectedGraph<>(Path.class);
		// create a visualization using JGraph, via an adapter
		jgAdapter = new JGraphModelAdapter<>(g);
		JGraph jgraph = new JGraph(jgAdapter);

		// Caso seja necessário modificar a forma como o Vertex é mostrado no
		// gráfico. Está no default.
		jgAdapter.setDefaultVertexAttributes(new Vertex().modificar());

		// Retorna os campos encontrados pela pesquisa realizada.
		try {
			camposRetornaveis = new SearcherSemFiltroDeDocumentos().pesquisar(procurarPor);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		List<String> pathsSeparados = new ArrayList<String>();
		// Separa todos os caminhos do resultado da pesquisa.
		pathsSeparados.addAll(new SepararString().camposDaPesquisa(camposRetornaveis));
		/*
		 * Adiciona cada pedaço do caminho como um retangulo no frame. Inicia um
		 * novo atributo no vértice para uso posterior para guardar todos os
		 * caminhos utilizados pelo vértice
		 */
		for (String string : pathsSeparados) {
			g.addVertex(string);
			jgAdapter.getVertexCell(string).getAttributes().put("path", "");
		}

		// Adiciona um MouseListener no JGraph para mostrar todos os caminhos
		// quando clicar no Vertice.
		jgraph.addMouseListener(new OuvinteDoMouse(jgraph, jgAdapter));

		/*
		 * Para todos os caminhos encontrados com o SepararString, é criado um
		 * edge(ligação entre vértices) do caminho atual para o próximo, caso o
		 * próximo caminho seja o CURRICULO-VITAE não é feito nada, pois
		 * constaria em uma conexão errada. É utilizado o atributo "path"
		 * inicializado para guardar todos os caminhos do vértice
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

		// Outros Layouts:
		// https://www.jgraph.com/downloads/jgraph/api/com/jgraph/layout/JGraphLayout.html
		final JGraphFacade jgraphFacade = new JGraphFacade(jgraph);
		final JGraphHierarchicalLayout layoutHierarquico = new JGraphHierarchicalLayout(true);
		try {
			layoutHierarquico.run(jgraphFacade);
		} catch (Exception e) {
			System.out.println("Não foi possivel criar gráfico hierarquico");
			JGraphCompactTreeLayout layoutDeArvore = new JGraphCompactTreeLayout();
			layoutDeArvore.setOrientation(SwingConstants.NORTH);
			layoutDeArvore.run(jgraphFacade);
		}
		/*
		 * Jgraph utiliza um Hashmap para armazenar todos os vértices e edges e
		 * suas respectivas posições, o nestedMap possui os mesmos dados porém
		 * em posições especificados pelo Layout utilizado. Para editar o
		 * gráfico original é utilizado o GraphLayoutCache().edit() que muda o
		 * local dos vertices e edges.
		 */
		final Map<?, ?> nestedMap = jgraphFacade.createNestedMap(true, true);
		jgraph.getGraphLayoutCache().edit(nestedMap);

		jgraph.getGraphLayoutCache().update();
		jgraph.refresh();
		return jgraph;
	}
}
