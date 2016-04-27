package br.ufsc.IndexacaoLucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.xml.sax.SAXException;

public class Indexador {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		FSDirectory indexDir = FSDirectory.open(new File("/home/fabio/Desktop/PIBIC/DocumentosIndexados").toPath());
		Analyzer analyzer = new StandardAnalyzer();
		HandlerDeXML handler = new HandlerDeXML();
		InputStream arquivoCurriculo = new FileInputStream("curriculos/Carina Lattes.xml");
		InputStream arquivoCurriculo2 = new FileInputStream("curriculos/Adriano Lattes.xml");
		InputStream arquivoCurriculo3 = new FileInputStream("curriculos/Christiane Lattes.xml");
		Document doc = handler.getDocument(arquivoCurriculo);
		Document doc2 = handler.getDocument(arquivoCurriculo2);
		Document doc3 = handler.getDocument(arquivoCurriculo3);

		IndexWriterConfig iwriterConf = new IndexWriterConfig(analyzer);
		IndexWriter iwriter = new IndexWriter(indexDir, iwriterConf);
		iwriter.addDocument(doc);
		iwriter.addDocument(doc2);
		iwriter.addDocument(doc3);
		iwriter.close();
	}
}
