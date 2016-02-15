package ufsc.indexacaoLucene;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXXMLDocument extends DefaultHandler {

	private Document document;
	private List<String> palavrasChaves = new ArrayList<String>();

	public Document getDocument(InputStream is) throws SAXException, IOException, ParserConfigurationException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser parser = spf.newSAXParser();
		parser.parse(is, this);
		return document;
	}

	public void startDocument() {
		document = new Document();
	}

	public void endDocument() {
		verificarPalavrasChavesIguais();
	}

	public void startElement(String uri, String localName, String chave, Attributes atributos) throws SAXException {
		for (int indexDoAtributo = 0; indexDoAtributo < atributos.getLength(); indexDoAtributo++) {
			if (chave.equals("DADOS-GERAIS") && atributos.getValue(indexDoAtributo) != "")
				document.add(new TextField(atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo),
						Field.Store.YES));
			if (chave.equals("ENDERECO-PROFISSIONAL") && atributos.getValue(indexDoAtributo) != ""
					&& verificarNumero(atributos.getValue(indexDoAtributo)))
				document.add(new TextField(atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo),
						Field.Store.YES));
			if (chave.equals("GRADUACAO") && atributos.getValue(indexDoAtributo) != ""
					&& verificarNumero(atributos.getValue(indexDoAtributo)))
				document.add(new TextField(atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo),
						Field.Store.YES));
			if (chave.equals("MESTRADO") && atributos.getValue(indexDoAtributo) != ""
					&& verificarNumero(atributos.getValue(indexDoAtributo)))
				document.add(new TextField(atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo),
						Field.Store.YES));
			if (chave.equals("PALAVRAS-CHAVE") && atributos.getValue(indexDoAtributo) != "")
				palavrasChaves.add(atributos.getValue(indexDoAtributo));
		}
	}

	private boolean verificarNumero(String valor) {
		try {
			if (Integer.parseInt(valor.substring(0, 1)) >= 0)
				return false;
		} catch (Exception e) {
		}
		return true;
	}

	private void verificarPalavrasChavesIguais() {
		String conjuntoDePalavrasChaves = "";
		for (int indexAncora = 0; indexAncora < palavrasChaves.size(); indexAncora++)
			for (int indexSecundario = indexAncora + 1; indexSecundario < palavrasChaves.size(); indexSecundario++)
				if (palavrasChaves.get(indexAncora).equals(palavrasChaves.get(indexSecundario))) {
					palavrasChaves.remove(indexSecundario);
					indexSecundario--;
				}

		for (int palavraChave = 0; palavraChave < palavrasChaves.size(); palavraChave++)
			conjuntoDePalavrasChaves += palavrasChaves.get(palavraChave) + ", ";

		document.add(new TextField("PALAVRAS-CHAVE", conjuntoDePalavrasChaves, Field.Store.YES));
	}
}
