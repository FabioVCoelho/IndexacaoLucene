package br.ufsc.IndexacaoLucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.xml.sax.SAXException;

public class Indexador {

	/*
	 * Indexador dos curriculos.
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		// Abre o diretório aonde serão armazenados os arquivos indexados.
		FSDirectory indexDir = NIOFSDirectory.open(new File("arquivosIndexados").toPath());
		/*
		 * Antes do texto ser indexado é passado pelo Analyzer que é
		 * especificado no IndexWriter, é responsável por retirar o texto de
		 * arquivos txt que devem ser indexados, caso seja utilizado outro tipo
		 * de arquivo, o mesmo deve ser extraído antes de passar pelo indexador.
		 */
		Analyzer analyzer = new StandardAnalyzer();
		// Handler utilizado para ler arquivos XML
		HandlerDeXML handler = new HandlerDeXML();
		/*
		 * Contém todas as configurações que são usados para criar um IndexWriter
		 */
		IndexWriterConfig iwriterConf = new IndexWriterConfig(analyzer);
		/*
		 * IndexWriter cria um novo indice ou abre um existe e adiciona, remove
		 * ou atualiza documentos nesse indice.
		 */
		IndexWriter iwriter = new IndexWriter(indexDir, iwriterConf);

		// Todos os arquivos que estão no diretório curriculos.
		String[] arquivos = FSDirectory.listAll(new File("curriculos").toPath());

		/*
		 * Para cada arquivo que está localizado na pasta curriculos o IndexWriter adiciona
		 * um documento que teve seu texto retirado pelo Handler.
		 */
		for (String string : arquivos) {
			iwriter.addDocument(handler.getDocument(new FileInputStream("curriculos/" + string)));
		}
		// Fecha indexador.
		iwriter.close();
		// Fecha diretório.
		indexDir.close();
	}
}
