# IndexacaoLucene

Caso não haja arquivos no arquivosIndexados é necessário rodar o Indexador.
O indexador não sobreescreve arquivos já existentes, caso seja rodado 2x, haverá arquivos duplicados

Como o xml do lattes é um xml customizado, não existe um Handler apropriado que retire as informações importantes do arquivo, para isso foi criado um Handler customizado para tratar apropriadamente o arquivo.

doc é o documento utilizado pelo Lucene para ser registrado no IndexWriter.


Método getDocument(InputStream is)

SAXParseFactory é utilizado para criar um novo SAX parser que referencia o InputStream arquivo XML, depois o parser chama os outros metodos para criar um documento para ser indexado pelo Lucene.
O SAXParser default, utiliza principalmente 5 métodos.

startDocument: que é chamado quando começa a leitura e analize do arquivo.

startElement: Toda vez que é encontrado um elemento no xml é chamado esse método.

characters: Passa todos os conteudos encotrados dentro dos elementos.
 
endElement: Quando termina o elemento encontrado no xml é chamado esse método.

endDocument: Quando não possui mais elementos para serem lidos, é chamado este método.

Exemplo do modo que o SAXParser funciona:

	<address-book>
		<contact type="individual">
			<name>Zane Pasolini</name>
		</contact>
	</address-book> 

	Start Document:
	Star Element:address-book
	Star Element:contact
	Star Element:name
	End Element:name
	End Element:contact
	End Element:address-book
	End Document:

parse(O arquivo, utilizando o DefaultHandler criado)

Método startDocument()
Cria o documento que será utilizado.

Método startElement()

Método characters.

Método endElement.

Método endDocument.


