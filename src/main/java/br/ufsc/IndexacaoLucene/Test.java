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
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.taxonomy.FastTaxonomyFacetCounts;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Test extends DefaultHandler {
	private FacetsConfig config;
	private Document document;
	private List<FacetField> book1;
	private List<String> caminho;
	private List<String> indexParaOConfig;

	public Test() {
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
			if (caminho.size() == 1 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), atributos.getQName(indexDoAtributo),
						atributos.getValue(indexDoAtributo)));
			if (caminho.size() == 2 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), caminho.get(1), atributos.getQName(indexDoAtributo),
						atributos.getValue(indexDoAtributo)));
			if (caminho.size() == 3 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), caminho.get(1), caminho.get(2),
						atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo)));
			if (caminho.size() == 4 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), caminho.get(1), caminho.get(2), caminho.get(3),
						atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo)));
			if (caminho.size() == 5 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), caminho.get(1), caminho.get(2), caminho.get(3), caminho.get(4),
						atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo)));
			if (caminho.size() == 6 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), caminho.get(1), caminho.get(2), caminho.get(3), caminho.get(4),
						caminho.get(5), atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo)));
			if (caminho.size() == 7 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), caminho.get(1), caminho.get(2), caminho.get(3), caminho.get(4),
						caminho.get(5), caminho.get(6), atributos.getQName(indexDoAtributo),
						atributos.getValue(indexDoAtributo)));
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

	
	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException,
			InstantiationException, IllegalAccessException {
		Test handler = new Test();
		InputStream arquivoCurriculo = new FileInputStream("curriculos/Carina Lattes.xml");
		InputStream arquivoCurriculo2 = new FileInputStream("curriculos/Adriano Lattes.xml");
		InputStream arquivoCurriculo3 = new FileInputStream("curriculos/Christiane Lattes.xml");
		Document doc = handler.getDocument(arquivoCurriculo);
		Document doc2 = handler.getDocument(arquivoCurriculo2);
		Document doc3 = handler.getDocument(arquivoCurriculo3);

		Directory taxoDir = new RAMDirectory();
		Directory indexDir = new RAMDirectory();
		DirectoryTaxonomyWriter taxoWriter = new DirectoryTaxonomyWriter(taxoDir);
		Analyzer analyzer = new StandardAnalyzer();
		FacetsConfig config = new FacetsConfig();
		for (int i = 0; i < handler.getIndexParaOConfig().size(); i++) {
			config.setHierarchical(handler.getIndexParaOConfig().get(i), true);
			config.setMultiValued(handler.getIndexParaOConfig().get(i), true);
		}
		IndexWriterConfig iwriterConf = new IndexWriterConfig(analyzer);
		IndexWriter iwriter = new IndexWriter(indexDir, iwriterConf);
		iwriter.addDocument(config.build(taxoWriter, doc));
		iwriter.addDocument(config.build(taxoWriter, doc2));
		iwriter.addDocument(config.build(taxoWriter, doc3));
		iwriter.close();
		taxoWriter.close();

		TaxonomyReader taxoReader = new DirectoryTaxonomyReader(taxoDir);

		IndexReader reader = DirectoryReader.open(indexDir);
		IndexSearcher searcher = new IndexSearcher(reader);

		List<FacetResult> results = new ArrayList<FacetResult>();
		FacetsCollector fc = new FacetsCollector();
		FacetsCollector.search(searcher, new MatchAllDocsQuery(), 10, fc);

		Facets facets = new FastTaxonomyFacetCounts(taxoReader, config, fc);
		results.add(facets.getTopChildren(10, "CURRICULO-VITAE", "DADOS-GERAIS", "NOME-COMPLETO"));

		System.out.println(results);
	}
}
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
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.taxonomy.FastTaxonomyFacetCounts;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.MultiCollector;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Test extends DefaultHandler {
	private FacetsConfig config;
	private Document document;
	private List<FacetField> book1;
	private List<String> caminho;
	private List<String> indexParaOConfig;

	public Test() {
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
			if (caminho.size() == 1 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), atributos.getQName(indexDoAtributo),
						atributos.getValue(indexDoAtributo)));
			if (caminho.size() == 2 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), caminho.get(1), atributos.getQName(indexDoAtributo),
						atributos.getValue(indexDoAtributo)));
			if (caminho.size() == 3 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), caminho.get(1), caminho.get(2),
						atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo)));
			if (caminho.size() == 4 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), caminho.get(1), caminho.get(2), caminho.get(3),
						atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo)));
			if (caminho.size() == 5 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), caminho.get(1), caminho.get(2), caminho.get(3), caminho.get(4),
						atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo)));
			if (caminho.size() == 6 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), caminho.get(1), caminho.get(2), caminho.get(3), caminho.get(4),
						caminho.get(5), atributos.getQName(indexDoAtributo), atributos.getValue(indexDoAtributo)));
			if (caminho.size() == 7 && atributos.getValue(indexDoAtributo) != "")
				book1.add(new FacetField(caminho.get(0), caminho.get(1), caminho.get(2), caminho.get(3), caminho.get(4),
						caminho.get(5), caminho.get(6), atributos.getQName(indexDoAtributo),
						atributos.getValue(indexDoAtributo)));
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

	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException,
			InstantiationException, IllegalAccessException, ParseException {
		Test handler = new Test();
		InputStream arquivoCurriculo = new FileInputStream("curriculos/TestDoXML.xml");
		Document doc = handler.getDocument(arquivoCurriculo);

		Directory taxoDir = new RAMDirectory();
		Directory indexDir = new RAMDirectory();
		DirectoryTaxonomyWriter taxoWriter = new DirectoryTaxonomyWriter(taxoDir);
		Analyzer analyzer = new StandardAnalyzer();
		FacetsConfig config = new FacetsConfig();
		for (int i = 0; i < handler.getIndexParaOConfig().size(); i++) {
			config.setHierarchical(handler.getIndexParaOConfig().get(i), true);
			config.setMultiValued(handler.getIndexParaOConfig().get(i), true);
		}
		IndexWriterConfig iwriterConf = new IndexWriterConfig(analyzer);
		IndexWriter iwriter = new IndexWriter(indexDir, iwriterConf);
		iwriter.addDocument(config.build(taxoWriter, doc));
		iwriter.close();
		taxoWriter.close();

		TaxonomyReader taxoReader = new DirectoryTaxonomyReader(taxoDir);

		IndexReader reader = DirectoryReader.open(indexDir);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector tdc = TopScoreDocCollector.create(10);

		List<FacetResult> results = new ArrayList<FacetResult>();
		FacetsCollector fc = new FacetsCollector();
		FacetsCollector.search(searcher, new MatchAllDocsQuery(), 10, MultiCollector.wrap(fc, tdc));
		Facets facets = new FastTaxonomyFacetCounts(taxoReader, config, fc);
		results.add(facets.getTopChildren(10, "FORMACAO-ACADEMICA-TITULACAO", "GRADUACAO", "NOME-DO-ORIENTADOR"));

		System.out.println(results.get(0));
		System.out.println(doc.getFields());
	}
}