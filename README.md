# IndexacaoLucene

Como o xml do lattes é um xml customizado, não existe um Handler apropriado que retire as informações importantes do arquivo, para isso foi criado um Handler customizado para tratar apropriadamente o arquivo.

doc é o documento utilizado pelo Lucene para ser registrado no IndexWriter.

palavrasChaves é uma lista com todas as PALAVRAS-CHAVES encontradas no xml do lattes da pessoa em questão.

Método getDocument(InputStream is)

SAXParseFactory é utilizado para criar um novo SAX parser que referencia o InputStream arquivo XML, depois o parser chama os outros metodos para criar um documento para ser indexado pelo Lucene.
O SAXParser default, utiliza principalmente 5 métodos.

startDocument: que é chamado quando começa a leitura e analize do arquivo.

startElement: Toda vez que é encontrado um elemento no xml é chamado esse método.

characters: Passa todos os conteudos encotrados dentro dos elementos.
 
endElement: Quando termina o elemento encontrado no xml é chamado esse método.

endDocument: Quando não possui mais elementos para serem lidos, é chamado este método.

Exemplo do modo que o SAXParser funciona:

address-book>
	contact type="individual">
		name>Zane Pasolini</name>
	/contact>
/address-book> 

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
O for é utilizado para percorrer todos os atributos encontrados no arquivo e colocados no documento criado para ser integrado ao lucene. Cada if é utilizado para um elemento considerado importante na obtenção de dados do arquivo xml. Porém existiam muitos dados em branco ou que não teriam utilidade nenhuma. Para isso utilizei 2 métodos para a indexação ficar mais limpa. O primeiro é pegar o valor de cada atributo que está passando e verificar se está em branco, se não houver valor, então, não é passado para o documento. E o outro seria verificar se o valor passado pelo elemento é um número, muitos dos números não eram de interesse nenhum, porém, alguns como o telefone para contato seria interessante guardar.(Não fiz isso ainda, queria saber se esse pensamento é válido). E um elemento que achei importante é o PALAVRA-CHAVE que existe no xml, neles estão todos os valores estudado pelo professor, desde uma colaboração no livro, quanto na tese de doutorado. Como as Palavras chaves estão espalhadas pelo arquivo, achei mais viavel guardar todas as palavras em uma lista e guarda-las no documento como um valor só. Porém quando fiz isso foram encontradas várias palavras repetidas. Portanto criei o método verificarPalavrasChavesIguais.

Método verificarPalavrasChavesIguais()
Utiliza um for de âncora e um for para passar por todos os outros elementos da lista. Quando encontra um valor igual ao da âncora o segundo valor é deletado, e para que a lista continue sem pular palavras é necessário diminuir um index do segundo for. por isso o -1. Após deletar as palavras iguais, adiciono todas as palavras como um valor no documento a ser indexado.

Método characters não utilizo.

Método endElement não utilizo.

Método endDocument quando termina o documento eu utilizo o método verificarPalavrasChavesIguais, assim possuo todas as palavras chaves do arquivo.

Método verificarNumero(String valor)
 Utiliza um try/catch, utilizando o if como o caso de ser um número e assim retornar um falso na condição. Porém como palavras não podem ser comparadas como int, esse método gera uma Expection, que não é tratada e portanto volta um true, informando que o valor é uma palavra e que deve ser adicionada ao documento.

O main foi utilizado para verificar se o documento estava saindo corretamente.
