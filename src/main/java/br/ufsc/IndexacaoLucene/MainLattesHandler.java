package br.ufsc.IndexacaoLucene;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.xml.sax.SAXException;

public class MainLattesHandler {
	
	public static void main(String[] args)
			throws IOException, ParserConfigurationException, SAXException, ParseException {
		LattesHandler handler = new LattesHandler();
		InputStream arquivoCurriculo = new FileInputStream("curriculos/Carina Lattes.xml");
		InputStream arquivoCurriculo2 = new FileInputStream("curriculos/Adriano Lattes.xml");
		InputStream arquivoCurriculo3 = new FileInputStream("curriculos/Christiane Lattes.xml");
		Document doc = handler.getDocument(arquivoCurriculo);
		Document doc2 = handler.getDocument(arquivoCurriculo2);
		Document doc3 = handler.getDocument(arquivoCurriculo3);

		Directory indexDir = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwriterConf = new IndexWriterConfig(analyzer);
		IndexWriter iwriter = new IndexWriter(indexDir, iwriterConf);
		iwriter.addDocument(doc);
		iwriter.addDocument(doc2);
		iwriter.addDocument(doc3);
		iwriter.close();

		IndexReader reader = DirectoryReader.open(indexDir);
		IndexSearcher searcher = new IndexSearcher(reader);

		List<IndexableField> fieldsDosDocs = new ArrayList<IndexableField>();
		int numeroMaximoDeFields = 0;
		for (int numeroDeDocumentos = 0; numeroDeDocumentos < reader.maxDoc(); numeroDeDocumentos++)
			if (reader.document(numeroDeDocumentos).getFields().size() > numeroMaximoDeFields) {
				fieldsDosDocs = reader.document(numeroDeDocumentos).getFields();
				numeroMaximoDeFields = fieldsDosDocs.size();
			}
		List<String> fields = new ArrayList<String>();
		List<ScoreDoc[]> hits = new ArrayList<ScoreDoc[]>();

		for (IndexableField ff : fieldsDosDocs) {
			fields.add(ff.name());
		}
		
		BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
		String[] cps = fields.toArray(new String[fields.size()]);
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(cps, analyzer);
		Query query = queryParser.parse("Friedrich");
		
		TopDocs docs = searcher.search(query, 10);
		hits.add(docs.scoreDocs);
	
		System.out.println("Documento em que a Query foi encontra: ");
		for (int i = 0; i < hits.size(); ++i) {
			for (int j = 0; j < hits.get(i).length; j++) {
				int docId = hits.get(i)[j].doc;
				Document d = searcher.doc(docId);
				System.out.println(
						docId + " " + d.get("CURRICULO-VITAE/DADOS-GERAIS/NOME-COMPLETO") + " " + hits.get(i)[j].score);
			}
		}
		reader.close();
	}
}
