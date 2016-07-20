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
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;

public class SearcherComFiltroDeDocumentos {

	public List<String> pesquisar(String string2) throws IOException, ParseException {
		long start = System.currentTimeMillis();
		// Local aonde está os arquivos indexados
		FSDirectory indexDir = NIOFSDirectory.open(new File("arquivosIndexados").toPath());
		// Analisador padrão do Lucene
		Analyzer analyzer = new StandardAnalyzer();
		/*
		 * Leitor dos arquivos Indexados
		 * 
		 * @param : local dos arquivos.
		 */
		IndexReader reader = DirectoryReader.open(indexDir);
		// Utilizado para procurar os valores do query.
		IndexSearcher searcher = new IndexSearcher(reader);
		/*
		 * Lista que armazena todos campos-chave que serão encontrados referente
		 * ao query solicitado. Ex: (CURRICULO-VITAE/DADOS-GERAIS).
		 */
		List<String> fields = new ArrayList<String>();
		/*
		 * Lista com todos os fields que serão utilizados nas buscas.
		 */
		List<IndexableField> fieldDosDocs = new ArrayList<IndexableField>();
		/*
		 * Adiciona ao fieldDosDocs todos os campos chave
		 */
		for (int numeroDeDocumentos = 0; numeroDeDocumentos < reader.maxDoc(); numeroDeDocumentos++)
			fieldDosDocs.addAll(reader.document(numeroDeDocumentos).getFields());
		/*
		 * Adiciona ao fields todos os fields em forma de String para
		 * transformar em String[] que é o modo de o Multiquery receber vários
		 * Campos-Chave
		 */
		for (IndexableField ff : fieldDosDocs)
			fields.add(ff.name());
		/*
		 * Indicar o valor que procura nos arquivos indexados
		 */
		String procurarPor = string2;
		/*
		 * Coloca a quantidade de fields que o MultiFieldQueryParser pode
		 * utilizar.
		 */
		BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
		/*
		 * Transforma String do fields em String[].
		 */
		String[] cps = fields.toArray(new String[fields.size()]);
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(cps, analyzer);
		/*
		 * Cria um query que é utilizado para realizar a busca com IndexSeacher.
		 */
		Query query = queryParser.parse(procurarPor);
		/*
		 * Collection que ao coletar o documento que bate com a query, já busca
		 * os fields que possuem o valor procurado.
		 */
		FieldCollector fs = new FieldCollector(procurarPor, searcher);
		// Faz a busca nos arquivos indexados. Passando pelo collection acima.
		searcher.search(query, fs);

		System.out.println("Achou: " + fs.retornaFields() + " campos com a palavra " + string2);
		System.out.println("Classe SearcherComFiltroDeDados rodou por " + (System.currentTimeMillis() - start) / 1000
				+ " segundos");
		/*
		 * Retorna todos os campos que foram encontrados nos documentos.
		 */
		return fs.retornaFields();
	}
}
