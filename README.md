
# Wallet Manager 
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/Pablo-Henrique/WalletAPI/blob/master/LICENSE) 
[![Build Status](https://travis-ci.org/azu/travis-badge.svg?branch=master)](https://travis-ci.org/Pablo-Henrique/WalletAPI)

# Sobre o projeto

Wallet Manager é uma aplicação back-end construída durante meu aprendizado sobre Spring-boot, utilizando APi RESTFull utilizando TDD, CI E CD.

A aplicação consiste em uma API de gerenciamento de carteiras, onde os usuarios poderão criar, modificar, deletar e inserir dados de maneira individual de uma carteira.  

<Br>

# Detalhes do projeto
### Esse projeto possui as seguintes características:

- Projeto criado com Spring Boot e Java 18
- Banco de dados Postgres com JPA e Spring Data JPA
- Versionamento de banco de dados com Flyway
- Testes com JUnit e Mockito com banco H2 em memória
- Caching com EhCache
- Nuvem do Heroku
- Integração contínua com TravisCI
- Project Lombok
- Documentação dos endpoints com Swagger
- Segurança da API com autenticação via tokens JWT

## Implantação em produção
- Back end: Heroku
- Banco de dados: PostgreSQL

# Como executar o projeto

Certifique-se de ter o Maven instalado e adicionado ao PATH de seu sistema operacional, assim como o Git, crie um banco de dados no postgres e altere o arquivo application.properties informando as credenciais para a aplicação acessar a base de dados, não se preocupe com a criação das tabelas, o flyway se encarregará dessa função.

## Back end
Pré-requisitos: 
- Java 18
- Apache Maven

```bash
# clonar repositório
git clone https://github.com/Pablo-Henrique/WalletAPI.git

# entrar na pasta do projeto back end
cd /<DIRETÓRIO DO PROJETO CLONADO>

# executar o projeto
./mvnw spring-boot:run
```

# Autenticando
Para fazer requisições aos demais endpoints da api deve-se criar um usuário. Utilize a rota /user com requisição HTTP post.
### Exemplo:
```Json
{
	"name": "Usuário",
	"email": "Usuário@email.com",
	"password": "123456"
}
```

"Obs: Os usuários possuem ROLES de acesso, porem não se preocupe, no corpo da requisição fica como opcional, pois a role vem como padrão na criação do usuário." 

<br>

## Após cadastrado

Agora você podera autenticar seu usuário e obter o token de acesso do tipo Bearer. Acesse o endpoin /auth e faça o login utilizando o email e senha do usuário cadastrado.

### Exemplo:
```Json
{
	"email": "Usuário@email.com",
	"password":"123456"
}
```
"Será gerado seu Bearer Token, que deve ser enviado no cabeçalho das requisições."


# Executando todos os testes
você podera executar os teste utilizando o seguinte comando no terminal dentro da pasta do projeto

```bash
mvn test
```

# Documentação
- Utilize a interface do Swagger para ter acesso a documentação dos endpoints, ela está disponível na url: 
http://localhost:8080/swagger-ui.html


# Heroku

- API estará disponivel na cloud do heroku: https://walletmanager-api.herokuapp.com/

"Acesse a documentação para obter as rotas disponível https://walletmanager-api.herokuapp.com/swagger-ui.html"
# Autor

- Pablo Henrique Anselmo

- Linkedin: https://www.linkedin.com/in/pablo-henrique-anselmo/
