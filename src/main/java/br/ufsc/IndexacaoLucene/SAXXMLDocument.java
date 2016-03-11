package br.ufsc.IndexacaoLucene;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.lucene.document.Document;
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.facet.FacetsConfig;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXXMLDocument extends DefaultHandler {

	private FacetsConfig config;
	private Document document;
	private List<FacetField> book1;
	private List<String> caminho;
	private List<String> indexParaOConfig;

	public SAXXMLDocument() {
		book1 = new ArrayList<FacetField>();
		caminho = new ArrayList<String>();
		config = new FacetsConfig();
		indexParaOConfig = new ArrayList<String>();
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
		for (int i = 0; i < book1.size(); i++)
			document.add(book1.get(i));
	}

	public void startElement(String uri, String localName, String chave, Attributes atributos) throws SAXException {
		config.setMultiValued(chave, true);
		config.setHierarchical(chave, true);
		caminho.add(chave);
		indexParaOConfig.add(chave);
		for (int indexDoAtributo = 0; indexDoAtributo < atributos.getLength(); indexDoAtributo++) {

			indexParaOConfig.add(atributos.getValue(indexDoAtributo));
			config.setMultiValued(atributos.getValue(indexDoAtributo), true);

			if (caminho.size() == 1 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(atributos.getValue(indexDoAtributo), caminho.get(0),
						atributos.getQName(indexDoAtributo)));
			if (caminho.size() == 2 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(atributos.getValue(indexDoAtributo), caminho.get(0), caminho.get(1),
						atributos.getQName(indexDoAtributo)));
			if (caminho.size() == 3 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(atributos.getValue(indexDoAtributo), caminho.get(0), caminho.get(1),
						caminho.get(2), atributos.getQName(indexDoAtributo)));
			if (caminho.size() == 4 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(atributos.getValue(indexDoAtributo), caminho.get(0), caminho.get(1),
						caminho.get(2), caminho.get(3), atributos.getQName(indexDoAtributo)));
			if (caminho.size() == 5 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(atributos.getValue(indexDoAtributo), caminho.get(0), caminho.get(1),
						caminho.get(2), caminho.get(3), caminho.get(4), atributos.getQName(indexDoAtributo)));
			if (caminho.size() == 6 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(atributos.getValue(indexDoAtributo), caminho.get(0), caminho.get(1),
						caminho.get(2), caminho.get(3), caminho.get(4), caminho.get(5),
						atributos.getQName(indexDoAtributo)));
			if (caminho.size() == 7 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(atributos.getValue(indexDoAtributo), caminho.get(0), caminho.get(1),
						caminho.get(2), caminho.get(3), caminho.get(4), caminho.get(5), caminho.get(6),
						atributos.getQName(indexDoAtributo)));
		}
	}

	public void endElement(String uri, String localName, String qName) {
		caminho.remove(qName);
	}

	public void characters(char ch[], int start, int length) {

	}

	public List<String> getIndexParaOConfig() {
		return indexParaOConfig;
	}

}
