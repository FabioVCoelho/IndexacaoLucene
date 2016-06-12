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

public class HandlerDeXML extends DefaultHandler {

	private Document document;
	private List<String> caminho;

	public HandlerDeXML() {
		caminho = new ArrayList<String>();
	}

	public Document getDocument(InputStream is) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser parser = spf.newSAXParser();
		parser.parse(is, this);
		return document;
	}

	@Override
	public void startDocument() throws SAXException {
		document = new Document();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		caminho.add(qName);
		for (int indexDoAtributo = 0; indexDoAtributo < attributes.getLength(); indexDoAtributo++) {
			String caminhoCompleto = "";
			for (int i = 0; i < caminho.size(); i++)
				caminhoCompleto += caminho.get(i) + "/";
			caminhoCompleto += attributes.getQName(indexDoAtributo);
			if (attributes.getValue(indexDoAtributo) != "")
				document.add(new TextField(caminhoCompleto, attributes.getValue(indexDoAtributo), Store.YES));
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		caminho.remove(qName);
	}

	@Override
	public void endDocument() throws SAXException {
	}
}
