ltasks4j
===========

Esta biblioteca pode ser usada para acessar os serviços do [LTasks](http://ltasks.com) de uma aplicação Java.

Download
--------

### Download do binário

Você pode obter uma cópia compilada do projeto na seção [SDK do LTasks](http://ltasks.com/sdk)

O ltasks4j requer a biblioteca Apache HTTP Client, que pode ser [obtida aqui](http://hc.apache.org/downloads.cgi).

### Usando Maven

* Inclua o seguinte repositório aos seus repositórios no pom.xml

```xml
<repository>
	<id>ltasks-releases</id>
	<name>LTasks Releases</name>
	<url>http://ltasks.com/repo/release/</url>
</repository>
```

* Se desejar usar versões snapshot inclua também:

```xml
<repository>
	<id>ltasks-snapshots</id>
	<url>http://ltasks.com/repo/snapshot/</url>
	<snapshots>
		<enabled>true</enabled>
	</snapshots>
</repository>
```

* Inclua a seguinte dependência: 

```xml
<dependency>
	<groupId>com.ltasks</groupId>
	<artifactId>ltasks4j</artifactId>
	<version>0.0.3</version>
</dependency>
```

Build
-----

Este projeto usa Maven. Se ainda não conhece Maven veja [este site](http://maven.apache.org/run-maven/index.html).

Como usar
---------

* Crie uma instância da classe `LtasksNameFinderClient` (substitua sua `APIKEY`por uma chave valida):

```java
LtasksNameFinderClient client = new LtasksNameFinderClient("APIKEY");
```

* Anote um texto:

```java
LtasksObject result = client.processText("Ele se encontrará com José em Brasília.");
```

* Você ainda pode anotar URLs e HTMLs:

```java
result = client.processUrl(new URL("http://pt.wikipedia.org/wiki/Cazuza"));
result = client.processHtml("<html><p>Ele se encontrará com José em Brasília.</p></html>");
```

* Acessar os resultados é simples:
	
```java
if (result.isProcessedOk()) {
	System.out.println("Foi possivel anotar o texto.");
	if (result.getMessage() != null) {
		// o servidor enviu uma mensagem
		System.out.println("Mensagem do servidor: "
				+ result.getMessage());
	}
	if (result.getSourceText() != null) {
		System.out.println("Texto fonte normalizado: "
				+ result.getSourceText());
	}
	if (result.getNamedEntities() != null) {
		for (NamedEntity entity : result.getNamedEntities()) {
			System.out.println("  tipo: " + entity.getType().value()
					+ " inicio: " + entity.getBegin() + " fim: "
					+ entity.getEnd() + " texto: "
					+ entity.getText());
		}
	}
} else {
	System.out
			.println("Houve um erro! Vamos tentar obter a mensagem de erro.");
	System.out.println("Mensagem do servidor: " + result.getMessage());
}
```

* Os métodos `processUrl` e `processHtml` aceitam um argumento do tipo `HtmlFilterOptions`. Exemplo:

```java
HtmlFilterOptions options = new HtmlFilterOptions();

// vamos selecionar apenas o div (e filhos) que tenha o atributo class com valor anId
// setInclude aceita uma lista, aqui crio uma lista só com um elemento.
options.setInclude(Collections.singletonList(new SimpleXPath("div",
		"class", "anId")));
		
// vamos excluir o parágrafo cujo id seja "a". Poderíamos criar a lista como fizemos 
// no include, mas para ilustrar vamos usar o SimpleXPath.parse(String), que aceita
// uma query XPath simplificada no estilo LTasks e devolve uma lista de SimpleXPath
options.setExclude(SimpleXPath.parse("//p[@id='a']"));

// Com HtmlFilter.none todos os elementos resultantes do include e exclude serão
// incluídos. Outras opções como HtmlFilter.standart e HtmlFilter.article podem 
// ser usadas para tentar automaticamente selecionar apenas os elementos mais 
// relevantes.
options.setFilter(HtmlFilter.none);

// finalmente efetuamos a chamada
result = client.processHtml(data, options);
```

Copyright
---------

Copyright (c) 2011 LTasks. See LICENSE for details.