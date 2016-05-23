package br.ufsc.treeJgraphT;

import javax.swing.JFrame;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
public class Demo extends JFrame {

	private static final long serialVersionUID = 1L;

	public Demo()	{
		super("JGraph");
		initGUI();
	}
	
	private void initGUI() {
		setSize(800,600);
		setLocationRelativeTo(null);
	}
	
	public DirectedGraph<String, DefaultEdge> vertices() {
		DirectedGraph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);
		directedGraph.addVertex("a");
		directedGraph.addVertex("b");
		directedGraph.addVertex("c");
		directedGraph.addVertex("d");
		directedGraph.addVertex("e");
		directedGraph.addVertex("f");
		directedGraph.addVertex("g");
		directedGraph.addVertex("h");
		directedGraph.addVertex("i");
		directedGraph.addEdge("a", "b");
		directedGraph.addEdge("b", "d");
		directedGraph.addEdge("d", "c");
		directedGraph.addEdge("c", "a");
		directedGraph.addEdge("e", "d");
		directedGraph.addEdge("e", "f");
		directedGraph.addEdge("f", "g");
		directedGraph.addEdge("g", "e");
		directedGraph.addEdge("h", "e");
		directedGraph.addEdge("i", "h");
		return directedGraph;
	}
}
