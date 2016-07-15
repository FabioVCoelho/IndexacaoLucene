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
		indexDir = FSDirectory.open(new File("arquivosIndexados").toPath());
		analyzer = new StandardAnalyzer();
		reader = DirectoryReader.open(indexDir);
		searcher = new IndexSearcher(reader);
		fields = new ArrayList<String>();
		fieldsDosDocs = new ArrayList<IndexableField>();
	}

	// SearcherSemFiltroDeDocumentos
	public List<String> retornarFields(int docID, String procurarPor) throws IOException, ParseException {
		fieldsDosDocs = reader.document(docID).getFields();
		for (IndexableField ff : fieldsDosDocs) {
			TopDocs td = searcher.search(new QueryParser(ff.name(), analyzer).parse(procurarPor), 1);
			if (td.totalHits > 0 && !fields.contains(ff.name()))
				fields.add(ff.name());
		}
		return fields;
	}

}
