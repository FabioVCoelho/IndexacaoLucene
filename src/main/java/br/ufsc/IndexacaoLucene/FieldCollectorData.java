package br.ufsc.IndexacaoLucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.LeafCollector;
import org.apache.lucene.search.Scorer;

public class FieldCollectorData implements LeafCollector {
	private String procurarPor;
	private List<String> fieldsEncontrado = new ArrayList<String>();

	public FieldCollectorData(String procurarPor) {
		this.procurarPor = procurarPor;
	}

	public void setScorer(Scorer scorer) throws IOException {
	}

	/*
	 * Utiliza um SearcherSemFiltroDeDocumentos para somente um Documento que
	 * foi coletado pelo query.
	 */
	public void collect(int doc) throws IOException {
		try {
			fieldsEncontrado.addAll(new BuscaDeFields().retornarFields(doc, procurarPor));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public List<String> retornaFields() {
		return fieldsEncontrado;
	}
}