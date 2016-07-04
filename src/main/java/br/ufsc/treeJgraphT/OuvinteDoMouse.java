package br.ufsc.treeJgraphT;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class OuvinteDoMouse implements MouseListener {

	private String string;

	public OuvinteDoMouse(String string) {
		this.string = string;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
		System.out.println(string);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
