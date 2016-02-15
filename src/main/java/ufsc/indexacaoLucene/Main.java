package ufsc.indexacaoLucene;

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

public class Main {

	public static void main(String[] args)
			throws IOException, ParseException, SAXException, ParserConfigurationException {
		SAXXMLDocument handler = new SAXXMLDocument();
		InputStream arquivoCurriculo = new FileInputStream("curriculos/Carina Lattes.xml");
		Document doc = handler.getDocument(arquivoCurriculo);
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwriterConf = new IndexWriterConfig(analyzer);
		Directory directory = new RAMDirectory();
		IndexWriter iwriter = new IndexWriter(directory, iwriterConf);
		iwriter.addDocument(doc);
		iwriter.close();

		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher isearch = new IndexSearcher(reader);
		Query q = new QueryParser("NOME-COMPLETO", analyzer).parse("Carina Friedrich Dorneles");
		TopDocs docs = isearch.search(q, 10);
		ScoreDoc[] hits = docs.scoreDocs;
		for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document d = isearch.doc(docId);
			System.out.println(d);
		}
	}

}
