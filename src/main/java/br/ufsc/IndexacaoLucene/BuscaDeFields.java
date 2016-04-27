package br.ufsc.IndexacaoLucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class BuscaDeFields {

	private FSDirectory indexDir;
	private Analyzer analyzer;
	private IndexReader reader;
	private IndexSearcher searcher;
	private List<String> fields;
	private List<IndexableField> fieldsDosDocs;

	public BuscaDeFields() throws IOException {
		indexDir = FSDirectory.open(new File("/home/fabio/Desktop/PIBIC/DocumentosIndexados").toPath());
		analyzer = new StandardAnalyzer();
		reader = DirectoryReader.open(indexDir);
		searcher = new IndexSearcher(reader);
		fields = new ArrayList<String>();
		fieldsDosDocs = new ArrayList<IndexableField>();
	}

	// Mesmo método utilizado no MainCampoCompleto, diferença que é utilizado
	// somente
	// um documento, e retorna os fields encontrados neste documento.
	public List<String> retornarFields(int docID, String procurarPor) throws IOException, ParseException {
		fieldsDosDocs = reader.document(docID).getFields();
		for (IndexableField ff : fieldsDosDocs) {
			QueryParser queryParser = new QueryParser(ff.name(), analyzer);
			TopDocs td = searcher.search(queryParser.parse(procurarPor), 1);
			if (td.totalHits > 0) {
				fields.add(ff.name());
			}
		}
		return fields;
	}

}
