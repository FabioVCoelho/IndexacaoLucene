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
			throws ParserConfigurationException, SAXException, IOException, ParseException {
		LattesHandler handler = new LattesHandler();
		InputStream arquivoCurriculo = new FileInputStream("curriculos/Carina Lattes.xml");
		Document doc = handler.getDocument(arquivoCurriculo);
		InputStream arquivoCurriculo2 = new FileInputStream("curriculos/Adriano Lattes.xml");
		Document doc2 = handler.getDocument(arquivoCurriculo2);
		InputStream arquivoCurriculo3 = new FileInputStream("curriculos/Eduardo Lattes.xml");
		Document doc3 = handler.getDocument(arquivoCurriculo3);
		InputStream arquivoCurriculo4 = new FileInputStream("curriculos/Marcelo Lattes.xml");
		Document doc4 = handler.getDocument(arquivoCurriculo4);

		Directory indexDir = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwriterConf = new IndexWriterConfig(analyzer);
		IndexWriter iwriter = new IndexWriter(indexDir, iwriterConf);
		iwriter.addDocument(doc);
		iwriter.addDocument(doc2);
		iwriter.addDocument(doc3);
		iwriter.addDocument(doc4);
		iwriter.close();

		IndexReader reader = DirectoryReader.open(indexDir);
		IndexSearcher searcher = new IndexSearcher(reader);

		String procurandoPor = "Carina Friedrich Dorneles";
		QueryParser queryparser = new QueryParser(procurandoPor, analyzer);
		Query query = queryparser.parse("CURRICULO-VITAE");
		TopDocs docs = searcher.search(query, 10);
		ScoreDoc[] hits = docs.scoreDocs;
		for (int i = 0; i < hits.length; i++) {
			int docId = hits[0].doc;
			Document d = searcher.doc(docId);
			System.out.println(d.getField(procurandoPor));
		}
	}
}
