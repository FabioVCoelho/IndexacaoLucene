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
import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HandlerDeXML extends DefaultHandler {

	private Document document;
	private List<String> caminho;
	private ListenableGraph<String, DefaultEdge> g;

	public HandlerDeXML() {
		caminho = new ArrayList<String>();
		g = new ListenableDirectedGraph<>(DefaultEdge.class);
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
		if (caminho.size() > 1) {
			g.addVertex(caminho.get(caminho.size() - 2));
			g.addVertex(qName);
			g.addEdge(caminho.get(caminho.size() - 2), qName);
		}
		for (int indexDoAtributo = 0; indexDoAtributo < attributes.getLength(); indexDoAtributo++) {
			String caminhoCompleto = "";
			for (int i = 0; i < caminho.size(); i++)
				caminhoCompleto += caminho.get(i) + "/";
			g.addVertex(qName);
			g.addVertex(attributes.getQName(indexDoAtributo));
			g.addEdge(qName, attributes.getQName(indexDoAtributo));
			caminhoCompleto += attributes.getQName(indexDoAtributo);
			if (attributes.getValue(indexDoAtributo) != "") {
				document.add(new TextField(caminhoCompleto, attributes.getValue(indexDoAtributo), Store.YES));
			}
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

	public ListenableGraph<String, DefaultEdge> retornaGraph() {
		return g;
	}
}
