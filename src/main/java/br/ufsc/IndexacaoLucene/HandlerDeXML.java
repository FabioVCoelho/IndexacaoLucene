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

	/*
	 * SAXParseFactory é utilizado para criar um novo SAX parser que referencia
	 * o InputStream, arquivo XML, depois o parser chama os metodos para criar
	 * um documento a ser indexado pelo Lucene.
	 */
	public Document getDocument(InputStream is) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser parser = spf.newSAXParser();
		parser.parse(is, this);
		return document;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * saxproject.org/apidoc/org/xml/sax/ContentHandler.html#startDocument()
	 * Receive notification of the beginning of the document.
	 */
	@Override
	public void startDocument() throws SAXException {
		document = new Document();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * saxproject.org/apidoc/org/xml/sax/ContentHandler.html#startElement(java.
	 * lang.String,%20java.lang.String,%20java.lang.String,%20org.xml.sax.
	 * Attributes) Ao entrar no elemento é guardado o seu valor no caminho e a
	 * cada elemento que é iniciado é adicionado o caminho a lista. Para recriar
	 * o caminho completo é utilizado o for e utilizado o mesmo para guardar o
	 * valor do seu atributo. Ex do curriculo: Curriculo-vitae tem como valor de
	 * atributo dados-gerais Curriculo-vitae/dados-gerais tem como valor de
	 * atributo nome-completo
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		caminho.add(qName);
		for (int indexDoAtributo = 0; indexDoAtributo < attributes.getLength(); indexDoAtributo++) {
			String caminhoCompleto = "";
			for (String caminho : caminho)
				caminhoCompleto += caminho + "/";
			caminhoCompleto += attributes.getQName(indexDoAtributo);
			if (attributes.getValue(indexDoAtributo) != "")
				document.add(new TextField(caminhoCompleto, attributes.getValue(indexDoAtributo), Store.YES));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * saxproject.org/apidoc/org/xml/sax/ContentHandler.html#characters(char[],%
	 * 20int,%20int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * saxproject.org/apidoc/org/xml/sax/ContentHandler.html#endElement(java.
	 * lang.String,%20java.lang.String,%20java.lang.String) Quando o elemento
	 * não possui mais valores ele é retirado da lista do caminho.
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		caminho.remove(qName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see saxproject.org/apidoc/org/xml/sax/ContentHandler.html#endDocument()
	 * Receive notification of the end of the document.
	 */
	@Override
	public void endDocument() throws SAXException {
	}
}
