package br.ufsc.treeJgraphT;

import java.util.ArrayList;
import java.util.List;

public class SepararString {
	// Lista em que ficará cada nível do path.
	private List<String> listaSeparada = new ArrayList<>();

	/*!
	 *	Este método recebe como parâmetro uma lista e transforma em um array de Strings
	 *  para poder usar o método split. Utilizando split("/") o caminho que era 
	 *  CURRICULO-VITAE/DADOS-GERAIS/NOME-COMPLETO, fica dividido em 3 Strings diferentes
	 *  sendo cada um deles um nível do path. Essas strings são adicionadas em uma lista
	 *  que vai ser retornada do método.
	 *  @Param camposRetornaveis: Lista em que será utilizado o método split para cada
	 *  elemento da lista.
	 */
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
