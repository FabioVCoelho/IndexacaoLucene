package br.ufsc.IndexacaoLucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

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
import org.xml.sax.SAXException;

public class SearcherSemFiltroDeDocumentos {

	public static void main(String[] args)
			throws IOException, ParserConfigurationException, SAXException, ParseException {
		long start = System.currentTimeMillis();
		// Local aonde está os arquivos indexados
		FSDirectory indexDir = FSDirectory.open(new File("/home/fabio/Desktop/PIBIC/DocumentosIndexados").toPath());
		// Analisador padrão do Lucene
		Analyzer analyzer = new StandardAnalyzer();
		// Leitor dos arquivos Indexados
		// Param : local dos arquivos.
		IndexReader reader = DirectoryReader.open(indexDir);
		// Utilizado para procurar os valores do query.
		IndexSearcher searcher = new IndexSearcher(reader);
		// Lista que armazena todos campos texto que serão encontrados referente
		// ao query
		// solicitado. Ex: (CURRICULO-VITAE/DADOS-GERAIS).
		List<String> fields = new ArrayList<String>();
		// Lista com todos os fields que serão utilizados nas buscas.
		List<IndexableField> fieldsDosDocs = new ArrayList<IndexableField>();
		// for ( Todos os arquivos que estão indexados)
		// for ( todos os fields(Campo-Chave) que estão nos documentos )
		// Procure nesse field pela query
		// Se o query foi encontrado, é colocado no Collection(TopDocs)
		// Se tiver algum valor no Collection(TopDocs), guarde no fields.
		for (int numeroDeDocumentos = 0; numeroDeDocumentos < reader.maxDoc(); numeroDeDocumentos++) {
			fieldsDosDocs = reader.document(numeroDeDocumentos).getFields();
			for (IndexableField ff : fieldsDosDocs) {
				QueryParser queryParser = new QueryParser(ff.name(), analyzer);
				TopDocs td = searcher.search(queryParser.parse("Adriano"), 1);
				if (td.totalHits > 0) {
					fields.add(ff.name());
				}
			}
		}
		reader.close();
		System.out.println("Método rodou por " + (System.currentTimeMillis() - start) / 1000 + " segundos");
	}
}