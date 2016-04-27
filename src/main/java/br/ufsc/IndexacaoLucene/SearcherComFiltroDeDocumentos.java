package br.ufsc.IndexacaoLucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SearcherComFiltroDeDocumentos {

	public static void main(String[] args) throws IOException, ParseException {
		long start = System.currentTimeMillis();
		// Local aonde está os arquivos indexados
		FSDirectory indexDir = FSDirectory.open(new File("/home/fabio/Desktop/PIBIC/DocumentosIndexados").toPath());
		// Analisador padrão do Lucene
		Analyzer analyzer = new StandardAnalyzer();
		// Leitor dos arquivos Indexados
		// Param : local dos arquivos.
		IndexReader reader = DirectoryReader.open(indexDir);
		// Utilizado para procurar os valores do query.
		IndexSearcher searcher = new IndexSearcher(reader);
		// Lista que armazena todos campos-chave que serão encontrados referente
		// ao query solicitado. Ex: (CURRICULO-VITAE/DADOS-GERAIS).
		List<String> fields = new ArrayList<String>();
		// Lista com todos os fields que serão utilizados nas buscas.
		List<IndexableField> fieldDosDocs = new ArrayList<IndexableField>();
		// fields que retornaram da pesquisa.
		List<String> fieldsEncontrado = new ArrayList<String>();
		// Adiciona ao fieldDosDocs todos os campos chave
		for (int numeroDeDocumentos = 0; numeroDeDocumentos < reader.maxDoc(); numeroDeDocumentos++)
			fieldDosDocs.addAll(reader.document(numeroDeDocumentos).getFields());
		// Adiciona ao fields todos os fields em forma de String para
		// transformar em String[]
		// que é o modo de o Multiquery receber vários Campos-Chave
		for (IndexableField ff : fieldDosDocs)
			fields.add(ff.name());

		// Indicar o valor que procura nos arquivos indexados
		String procurarPor = "Adriano";
		// Coloca a quantidade de fields que o MultiFieldQueryParser pode
		// utilizar.
		BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
		// Transforma String do fields em String[].
		String[] cps = fields.toArray(new String[fields.size()]);
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(cps, analyzer);
		// Cria um query que é utilizado para realizar a busca com IndexSeacher.
		Query query = queryParser.parse(procurarPor);
		// Faz a procura em todos os arquivos indexados e caso o documento tenha
		// um score
		// maior que um valor (x declarado pelo lucene) o mesmo é dado com
		// documento válido
		// e armazenado no TopDocs.
		TopDocs docs = searcher.search(query, 10);
		// hits é um Array de docs, onde fica armazenado qual o número do
		// documento que foi
		// armazenado no TopDocs.
		ScoreDoc[] hits = docs.scoreDocs;
		// Para todos os hits(Documentos) fieldsEncontrados vai receber os
		// valores dos campos-chave.
		for (int i = 0; i < hits.length; ++i)
			fieldsEncontrado = new BuscaDeFields().retornarFields(hits[i].doc, procurarPor);
		System.out.println("Método rodou por " + (System.currentTimeMillis() - start) / 1000 + " segundos");
	}
}
