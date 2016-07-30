package br.ufsc.JTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

class Folder extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;

	public Folder(String string) {
		super(string);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.DefaultMutableTreeNode#add(javax.swing.tree.
	 * MutableTreeNode) Modificado para não adicionar dois nodos iguais, caso o
	 * nodo já exista não deve-se adiciona-lo.
	 */
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

	/*
	 * Cria o filho do nodo passado como string.
	 */
	public void setFilho(String string) {
		Folder filho = new Folder(string);
		add(filho);
	}

	/*
	 * Busca o filho do nodo passado como string. Caso não encontrado retorna
	 * null.
	 */
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
