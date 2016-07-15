package br.ufsc.treeJgraphT;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgrapht.ext.JGraphModelAdapter;

public class OuvinteDoMouse implements MouseListener {

	private JGraph jgraph;
	private JGraphModelAdapter<String, Path> jgAdapter;

	public OuvinteDoMouse(JGraph jgraph, JGraphModelAdapter<String, Path> jgAdapter) {
		this.jgraph = jgraph;
		this.jgAdapter = jgAdapter;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object location = jgraph.getFirstCellForLocation(e.getX(), e.getY());
		if (location != null) {
			DefaultGraphCell cell = jgAdapter.getVertexCell(location.toString());
			System.out.println(cell.getAttributes().get("path"));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
