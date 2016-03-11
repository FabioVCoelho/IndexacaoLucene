package br.ufsc.IndexacaoLucene;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LattesHandler extends DefaultHandler {
	private Document document;
	private List<String> caminho;

	public LattesHandler() {
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
				pathTamanhoUm(atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo));
			}
			if (caminho.size() == 2 && atributos.getValue(indexDoAtributo) != "") {
				pathTamanhoDois(atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo));
			}
			if (caminho.size() == 3 && atributos.getValue(indexDoAtributo) != "") {
				pathTamanhoTres(atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo));

			}
			if (caminho.size() == 4 && atributos.getValue(indexDoAtributo) != "") {
				pathTamanhoQuatro(atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo));
			}
			if (caminho.size() == 5 && atributos.getValue(indexDoAtributo) != "") {
				pathTamanhoCinco(atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo));
			}
		}
	}

	public void endElement(String uri, String localName, String qName) {
		caminho.remove(qName);
	}

	public void characters(char ch[], int start, int length) {

	}

	private void pathTamanhoCinco(String fieldName, String value) {
		document.add(new TextField(caminho.get(1), caminho.get(0), Store.YES));
		document.add(new TextField(caminho.get(2), caminho.get(0) + "/" + caminho.get(1), Store.YES));
		document.add(
				new TextField(caminho.get(3), caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2), Store.YES));
		document.add(new TextField(caminho.get(4),
				caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2) + "/" + caminho.get(3), Store.YES));
		document.add(new TextField(fieldName, caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2) + "/"
				+ caminho.get(3) + "/" + caminho.get(4), Store.YES));
		document.add(new TextField(value, caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2) + "/"
				+ caminho.get(3) + "/" + caminho.get(4) + "/" + fieldName, Store.YES));

	}

	private void pathTamanhoQuatro(String fieldName, String value) {
		document.add(new TextField(caminho.get(1), caminho.get(0), Store.YES));
		document.add(new TextField(caminho.get(2), caminho.get(0) + "/" + caminho.get(1), Store.YES));
		document.add(
				new TextField(caminho.get(3), caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2), Store.YES));
		document.add(new TextField(fieldName,
				caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2) + "/" + caminho.get(3), Store.YES));
		document.add(new TextField(value,
				caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2) + "/" + caminho.get(3) + "/" + fieldName,
				Store.YES));
	}

	private void pathTamanhoTres(String fieldName, String value) {
		document.add(new TextField(caminho.get(1), caminho.get(0), Store.YES));
		document.add(new TextField(caminho.get(2), caminho.get(0) + "/" + caminho.get(1), Store.YES));
		document.add(new TextField(fieldName, caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2), Store.YES));
		document.add(new TextField(value,
				caminho.get(0) + "/" + caminho.get(1) + "/" + caminho.get(2) + "/" + fieldName, Store.YES));
	}

	private void pathTamanhoDois(String fieldName, String value) {
		document.add(new TextField(caminho.get(1), caminho.get(0), Store.YES));
		document.add(new TextField(fieldName, caminho.get(0) + "/" + caminho.get(1), Store.YES));
		document.add(new TextField(value, caminho.get(0) + "/" + caminho.get(1) + "/" + fieldName, Store.YES));
	}

	private void pathTamanhoUm(String fieldName, String value) {
		document.add(new TextField(fieldName, caminho.get(0), Store.YES));
		document.add(new TextField(value, caminho.get(0) + "/" + fieldName, Store.YES));
	}
}