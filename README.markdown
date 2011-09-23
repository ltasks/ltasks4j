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
	<url>http://ltasks.com/repo/release/snapshot/</url>
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
	<version>0.0.1-SNAPSHOT</version>
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
result = client.processHtml(new URL("<html><p>Ele se encontrará com José em Brasília.</p></html>"));
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

Copyright
---------

Copyright (c) 2011 LTasks. See LICENSE for details.