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

	public LeafCollector getLeafCollector(LeafReaderContext context) throws IOException {
		return data;
	}

	public boolean needsScores() {
		return false;
	}

	// Retorna todos os fields encontrados com a string passada como Parametro
	// no construtor.
	public List<String> retornaFields() {
		return data.retornaFields();
	}
}
