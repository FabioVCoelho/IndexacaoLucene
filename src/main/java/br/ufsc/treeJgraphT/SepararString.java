package br.ufsc.treeJgraphT;

import java.util.ArrayList;
import java.util.List;

public class SepararString {
	private List<String> listaSeparada = new ArrayList<>();

	public List<String> camposDaPesquisa(List<String> camposRetornaveis) {
		String[] y = camposRetornaveis.toArray(new String[0]);
		for (int i = 0; i < y.length; i++) {
			String[] splitado = y[i].split("/");
			for (String string : splitado) {
				listaSeparada.add(string);
			}
		}
		return listaSeparada;
	}

}
