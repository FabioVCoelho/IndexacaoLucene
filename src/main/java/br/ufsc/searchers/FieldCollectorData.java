package br.ufsc.searchers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.LeafCollector;
import org.apache.lucene.search.Scorer;

public class FieldCollectorData implements LeafCollector {
	private String procurarPor;
	private List<String> fieldsEncontrado = new ArrayList<String>();
	private IndexSearcher searcher;

	public FieldCollectorData(String procurarPor, IndexSearcher searcher) {
		this.procurarPor = procurarPor;
		this.searcher = searcher;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see lucene.apache.org/core/5_5_2/core/org/apache/lucene/search/
	 * LeafCollector.html#setScorer(org.apache.lucene.search.Scorer)
	 */
	public void setScorer(Scorer scorer) throws IOException {
	}


	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see lucene.apache.org/core/5_5_2/core/org/apache/lucene/search/
	 * LeafCollector.html#collect(int)
	 * Utiliza um SearcherSemFiltroDeDocumentos para somente um Documento que
	 * foi coletado pelo query.
	 */
	public void collect(int doc) throws IOException {
		try {
			fieldsEncontrado.addAll(new BuscaDeFields(searcher).retornarFields(doc, procurarPor));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public List<String> retornaFields() {
		return fieldsEncontrado;
	}
}