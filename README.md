# IndexacaoLucene

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

FacetsConfig config: Utilizado para indexar os elementos como hierarquicos e com vários valores.
Document document: documento utilizado para indexação.
List<FacetField> book1: Lista com todos os fields que serão indexados no documento.
List<String> caminho: Lista com o caminho percorrido, cada vez que o método starElement() é chamado, esta lista adiciona o nome do elemento se for diferente do antigo.
List<String> indexParaOConfig: configuração utilizada para a indexação, necessário para não deixar o config como static.

config.setMultiValued(chave, true): Utilizado para que um campo possa ter mais que um valor.
config.setHierarchical(chave, true): Utlizado para informar que o elemento é do tipo hierarchical, ou seja, possui um caminho(path)
	
new FacetField(dim, path) : FacetField foi utilizado para indexações do tipo Taxonomy, aonde o arquivo a ser indexado possui um caminho para ser percorrido antes de chegar em seu valor. FacetField é utilizado para categorização de campos, por exemplo quando se necessita buscar todos os autores, cria-se vários FacetField("autor", "nome do autor"). Quando for criada a busca por autor, retornará todos os nomes contidos nesse campo. Para utilizar a indexação do tipo Taxonomy, se utiliza um "dim", que é o rootNode e o "path" seria o "resto" do caminho a ser indexado. Sendo o ultimo, o valor.

Outro modo de fazer, seria utilizando o TextField do lucene, e recriando um caminho sobre os elementos em que o SAXHandler está passando, para isso utilizei uma lista para guardar os valores do elemento, a List não grava nomes iguais, portanto, mesmo entrando várias vezes no mesmo elemento, o seu tamanho continua o mesmo. E quando o SAXHandler sair do elemento é retirado o valor do elemento.
Para registrar todos os elementos que o SAXHandler passa, é necessários criar vários TextFields, por exemplo, se for registrar o valor do FAX, é necessário entrar nos seguintes elementos: CURRICULO-VITAE -> DADOS-GERAIS -> ENDERECO -> ENDERECO-PROFISSIONAL -> FAX
neste caso o caminho teria no seu primeiro elemento CURRICULO-VITAE, segundo elemento DADOS-GERAIS, e assim por diante, até o quinto elemento FAX, então para recriar um xml, o DADOS-GERAIS tem que ser um valor do CURRICULO-VITAE, pois necessita-se passar por esse elemento, e assim sucessivamente, se fosse colocado somente o valor do FAX sem utilizar essa ideia, seria somente necessário entrar em um elemento, que seria o caminho completo até o FAX, não podendo procurar por exemplo, o que está dentro do elemento ENDERECO.
