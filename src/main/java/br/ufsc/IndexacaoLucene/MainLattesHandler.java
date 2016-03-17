package br.ufsc.IndexacaoLucene;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
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
		CampoCompleto handler = new CampoCompleto();
		InputStream arquivoCurriculo = new FileInputStream("curriculos/Carina Lattes.xml");
		Document doc = handler.getDocument(arquivoCurriculo);

		Directory indexDir = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwriterConf = new IndexWriterConfig(analyzer);
		IndexWriter iwriter = new IndexWriter(indexDir, iwriterConf);
		iwriter.addDocument(doc);
		iwriter.close();

		IndexReader reader = DirectoryReader.open(indexDir);
		IndexSearcher searcher = new IndexSearcher(reader);

		List<IndexableField> a = doc.getFields();
		List<String> listaDeFields = new ArrayList<String>();
		for (int i = 0; i < 1024; i++)
			listaDeFields.add(a.get(i).name()); //Como o BooleanQuery chega no máximo a 1024 operações.
		Object[] fieldsDoDocumento = listaDeFields.toArray(); // Utilizado para passar a List em String[] de Fields, que o MultiField recebe como parametro
		String[] stringDasFields = Arrays.copyOf(fieldsDoDocumento, fieldsDoDocumento.length, String[].class); 
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(stringDasFields, analyzer);
		Query query = queryParser.parse("Carina Fridrich Dorneles");
		TopDocs docs = searcher.search(query, 10);

		ScoreDoc[] hits = docs.scoreDocs;
		System.out.println(docs.totalHits);
	}
}
