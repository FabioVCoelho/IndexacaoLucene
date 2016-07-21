package br.ufsc.treeJgraphT;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.GraphConstants;

public class Vertex {
	/*
	 * Vertex padrão utilizado pelo JGraph, utilizando o GraphConstants pode-se
	 * modificar como o vértice é mostrado no gráfico. Por exemplo: retirando o
	 * setBounds e utilizando o setAutoSize(true), os vértices irão ser
	 * inseridos de modo a não se sobrepor e com o nome completo.
	 */
	public AttributeMap modificar() {
		AttributeMap map = new AttributeMap();
		Color c = Color.decode("#FF9900");

		GraphConstants.setBounds(map, new Rectangle2D.Double(50, 50, 90, 30));
		GraphConstants.setBorder(map, BorderFactory.createRaisedBevelBorder());
		GraphConstants.setBackground(map, c);
		GraphConstants.setForeground(map, Color.white);
		GraphConstants.setFont(map, GraphConstants.DEFAULTFONT.deriveFont(Font.BOLD, 12));
		GraphConstants.setOpaque(map, true);

		return map;
	}

}
