#  Microservice relativo a criação de propostas
  Neste desafio preciso desenvolver um Microservice para suportar criação de propostas.
  A ideia é trabalhar com Docker, Feign, Métricas e Healthcheck, criando código que seja suficiente para a funcionalidade.

## Começando
Para executar o projeto, será necessário instalar os seguintes programas:

- [Java 11+](https://openjdk.java.net/projects/jdk/11/)
- [Maven 3+](https://maven.apache.org/download.cgi)
- [Postman](https://www.postman.com/downloads/) ou [Imsominia](https://insomnia.rest/download)
- MySQL(8.0.23)

## Observação
* Projeto usa ecossistema Spring
* MySQL como banco de dados

## Desenvolvimento

* Para iniciar o desenvolvimento Tenha uma IDE(eclipse com STS) e clone o projeto do GitHub num diretório:

```shell
	cd "<seu diretório(workspace)>"
	git clone https://github.com/fmchagas/orange-talents-04-template-proposta.git
```

* Rode a aplicação

```shell
	cd "<diretório raiz da aplicação>"
	./mvnw spring-boot:run
	ou ./mvnw spring-boot:start
```

* Pare a aplicação se usar start

```shell
    ctrl + c
    ./mvnw spring-boot:stop
```

* Um desafio extra adicionar documentação da api com springfox -> localhost:8080/swagger-ui.html
- [Documentação](http://localhost:8080/swagger-ui.html) 