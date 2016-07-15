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
	 * Attributes) Ao entrar no elemento é guardado o seu valor no caminho, no
	 * caso do curriculo, o primeiro valor é o do CURRICULO-VITAE e para cada
	 * caminhoCompleto é atribuido o valor do dado.
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
	 * lang.String,%20java.lang.String,%20java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		caminho.remove(qName);
	}

	/*
	 * (non-Javadoc)
	 * @see saxproject.org/apidoc/org/xml/sax/ContentHandler.html#endDocument()
	 * Receive notification of the end of the document. 
	 */
	@Override
	public void endDocument() throws SAXException {
	}
}
