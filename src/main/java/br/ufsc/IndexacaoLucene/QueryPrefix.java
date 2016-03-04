package br.ufsc.IndexacaoLucene;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class QueryPrefix extends DefaultHandler {
	private Document document;
	private List<String> caminho;

	public QueryPrefix() {
		caminho = new ArrayList<String>();
	}

	public Document getDocument(InputStream is) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser parser = spf.newSAXParser();
		parser.parse(is, this);
		return document;
	}

	public void startDocument() {
		document = new Document();
	}

	public void endDocument() {
	}

	public void startElement(String uri, String localName, String chave, Attributes atributos) throws SAXException {
		caminho.add(chave);
		for (int indexDoAtributo = 0; indexDoAtributo < atributos.getLength(); indexDoAtributo++) {
			if (caminho.size() == 1 && atributos.getValue(indexDoAtributo) != "") {
				pathTamanhoUm(atributos, indexDoAtributo);
			}
			if (caminho.size() == 2 && atributos.getValue(indexDoAtributo) != "") {
				pathTamanhoDois(atributos, indexDoAtributo);
			}
			if (caminho.size() == 3 && atributos.getValue(indexDoAtributo) != "") {
				pathTamanhoTres(atributos, indexDoAtributo);

			}
			if (caminho.size() == 4 && atributos.getValue(indexDoAtributo) != "") {
				pathTamanhoQuatro(atributos, indexDoAtributo);
			}
		}
	}

	public void endElement(String uri, String localName, String qName) {
		caminho.remove(qName);
	}

	public void characters(char ch[], int start, int length) {

	}

	private void pathTamanhoQuatro(Attributes atributos, int indexDoAtributo) {
		document.add(new TextField(caminho.get(0), caminho.get(1), Store.YES));
		document.add(new TextField(caminho.get(0) + "/" + caminho.get(1), caminho.get(2), Store.YES));
		document.add(
				new TextField(caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2), caminho.get(3), Store.YES));
		document.add(new TextField(caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2) + "/" + caminho.get(3),
				atributos.getQName(indexDoAtributo), Store.YES));
		document.add(new TextField(caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2) + "/" + caminho.get(3)
				+ "/" + atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo), Store.YES));
	}

	private void pathTamanhoTres(Attributes atributos, int indexDoAtributo) {
		document.add(new TextField(caminho.get(0), caminho.get(1), Store.YES));
		document.add(new TextField(caminho.get(0) + "/" + caminho.get(1), caminho.get(2), Store.YES));
		document.add(new TextField(caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2),
				atributos.getQName(indexDoAtributo), Store.YES));
		document.add(new TextField(caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2) + "/"
				+ atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo), Store.YES));
	}

	private void pathTamanhoDois(Attributes atributos, int indexDoAtributo) {
		document.add(new TextField(caminho.get(0), caminho.get(1), Store.YES));
		document.add(
				new TextField(caminho.get(0) + "/" + caminho.get(1), atributos.getQName(indexDoAtributo), Store.YES));
		document.add(new TextField(caminho.get(0) + "/" + caminho.get(1) + "/" + atributos.getQName(indexDoAtributo),
				atributos.getValue(indexDoAtributo), Store.YES));
	}

	private void pathTamanhoUm(Attributes atributos, int indexDoAtributo) {
		document.add(new TextField(caminho.get(0), atributos.getQName(indexDoAtributo), Store.YES));
		document.add(new TextField(caminho.get(0) + "/" + atributos.getQName(indexDoAtributo),
				atributos.getValue(indexDoAtributo), Store.YES));
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		QueryPrefix handler = new QueryPrefix();
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

		Term term = new Term("CURRICULO-VITAE"); // Term("FieldName", "Value")
		Query query = new PrefixQuery(term);
		TopDocs docs = searcher.search(query, 10);
		ScoreDoc[] hits = docs.scoreDocs;

		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println(d.getField("CURRICULO-VITAE"));
			// Só mostra o primeiro valor encontrado.
		}
	}
}