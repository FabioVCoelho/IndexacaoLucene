package br.ufsc.JTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

class Folder extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;

	public Folder(String string) {
		super(string);
	}

	@Override
	public void add(MutableTreeNode novoFilho) {
		String novoFilhoString = (String) ((DefaultMutableTreeNode) novoFilho).getUserObject();
		if (children != null) {
			if (getFilho(novoFilhoString) != null)
				return;
			super.add(novoFilho);
		}
		if (children == null)
			super.add(novoFilho);
	}

	public void setFilho(String string) {
		Folder filho = new Folder(string);
		add(filho);
	}

	public Folder getFilho(String string) {
		Folder filho;
		for (Object childObj : children) {
			filho = (Folder) childObj;
			if (filho.getUserObject().equals(string)) {
				return filho;
			}
		}
		return null;
	}
}
