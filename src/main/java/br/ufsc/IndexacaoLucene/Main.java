package br.ufsc.IndexacaoLucene;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
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
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args)
			throws IOException, ParseException, SAXException, ParserConfigurationException {
		SAXXMLDocument handler = new SAXXMLDocument();
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