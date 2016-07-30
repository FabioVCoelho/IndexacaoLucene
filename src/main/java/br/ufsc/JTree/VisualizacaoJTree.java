package br.ufsc.JTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;

import org.apache.lucene.queryparser.classic.ParseException;

import br.ufsc.searchers.SearcherSemFiltroDeDocumentos;

public class VisualizacaoJTree {

	public JTree pesquisar(String buscarPor) {
		// Como todos os campos começam com "CURRICULO-VITAE", este será o root
		// do JTree
		Folder root = new Folder("CURRICULO-VITAE");

		List<String> camposRetornaveis = new ArrayList<String>();
		try {
			camposRetornaveis = new SearcherSemFiltroDeDocumentos().pesquisar(buscarPor);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		// Para cada campo retornado, este é passado por um split, que separa
		// cada caminho, caso o caminho seja maior ou igual a dois, então é
		// criado um root temporario e após adicionar o seu filho, este passa a
		// ser o root. E quando chegamos no ultimo, este é adicionado ao JTree.
		for (String fullPath : camposRetornaveis) {
			String[] paths = fullPath.split("/");
			if (paths.length >= 2) {
				Folder tempRoot = root;
				for (int i = 1; i < paths.length; i++) {
					if (i < paths.length - 1) {
						tempRoot.setFilho(paths[i]);
						tempRoot = tempRoot.getFilho(paths[i]);
					} else {
						tempRoot.add(new Folder(paths[i]));
					}
				}
			}
		}
		return new JTree(root);
	}
}