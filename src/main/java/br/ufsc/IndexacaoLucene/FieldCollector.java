package br.ufsc.IndexacaoLucene;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.LeafCollector;

public class FieldCollector implements Collector {

	private FieldCollectorData data;

	public FieldCollector(String procurarPor, IndexSearcher searcher) {
		data = new FieldCollectorData(procurarPor, searcher);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * lucene.apache.org/core/5_5_2/core/org/apache/lucene/search/Collector.html
	 * #getLeafCollector(org.apache.lucene.index.LeafReaderContext)
	 */
	public LeafCollector getLeafCollector(LeafReaderContext context) throws IOException {
		return data;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * lucene.apache.org/core/5_5_2/core/org/apache/lucene/search/Collector.html
	 * #needsScores()
	 */
	public boolean needsScores() {
		return false;
	}

	// Retorna todos os fields encontrados com a string passada como Parametro
	// no construtor.
	public List<String> retornaFields() {
		return data.retornaFields();
	}
}
