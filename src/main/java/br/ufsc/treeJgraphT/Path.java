package br.ufsc.treeJgraphT;

import org.jgrapht.graph.DefaultEdge;

public class Path extends DefaultEdge {

	private static final long serialVersionUID = 1L;

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgrapht.graph.DefaultEdge#toString() O Edge seria a flecha entre
	 * vertices, o toString mostrava os valores que estavam sendo ligados,
	 * gerando uma poluição visual
	 */
	public String toString() {
		return "";
	}

}
