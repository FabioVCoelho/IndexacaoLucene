package br.ufsc.searchers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;

public class SearchTest {

	public static void main(String[] args) throws IOException, ParseException {
		long start = System.currentTimeMillis();
		FSDirectory indexDir = NIOFSDirectory.open(new File("arquivosIndexados").toPath());
		Analyzer analyzer = new StandardAnalyzer();
		IndexReader reader = DirectoryReader.open(indexDir);
		IndexSearcher searcher = new IndexSearcher(reader);
		List<String> fields = new ArrayList<String>();
		List<IndexableField> fieldDosDocs = new ArrayList<IndexableField>();

		String procurarPor = "Carina";
		System.out.println("Iniciação das variaveis: " + (System.currentTimeMillis() - start) + " milisegundos");

		long start2 = System.currentTimeMillis();

		// SearchComFiltroDeDocumento
		for (int numeroDeDocumentos = 0; numeroDeDocumentos < reader.maxDoc(); numeroDeDocumentos++)
			fieldDosDocs.addAll(reader.document(numeroDeDocumentos).getFields());
		for (IndexableField ff : fieldDosDocs)
			fields.add(ff.name());
		BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
		String[] cps = fields.toArray(new String[fields.size()]);
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(cps, analyzer);
		Query query = queryParser.parse(procurarPor);
		FieldCollector fs = new FieldCollector(procurarPor, searcher);
		searcher.search(query, fs);
		List<String> camposRetornaveis = fs.retornaFields();
		System.out.println("Método ComFiltro por " + (System.currentTimeMillis() - start2) / 1000 + " segundos");

		long start3 = System.currentTimeMillis();
		// SearchSemFiltroDeDocumentos
		fields = new ArrayList<String>();
		for (int numeroDeDocumentos = 0; numeroDeDocumentos < reader.maxDoc(); numeroDeDocumentos++) {
			fieldDosDocs = reader.document(numeroDeDocumentos).getFields();
			for (IndexableField ff : fieldDosDocs) {
				QueryParser queryParser2 = new QueryParser(ff.name(), analyzer);
				TopDocs td = searcher.search(queryParser2.parse(procurarPor), 1);
				if (td.totalHits > 0 && !fields.contains(ff.name())) {
					fields.add(ff.name());
				}
			}
		}
		System.out.println("Método SemFiltro por " + (System.currentTimeMillis() - start3) / 1000 + " segundos");
		System.out.println("Campos encontrados pelo ComFiltro: " + camposRetornaveis.size());
		System.out.println("Campos encontrados pelo SemFiltro: " + fields.size());
	}
}
