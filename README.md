# API de Transferência Financeira
Este projeto é uma API RESTful que permite aos usuários fazer transferências financeiras e calcula a taxa com base no tipo de transferência e no número de dias entre a data da transferência e a data programada.

## Tecnologias Utilizadas
+ Java: 18
+ Spring Boot: 3.0.5
+ Spring Boot Data JPA: 3.0.5
+ Spring Boot Web: 3.0.5
+ H2 Database
+ Spring Boot Test: 3.0.5
+ Maven 4
+ JUnit 5
+ Mockito

## Getting Started
Para começar com o projeto, clone o repositório e execute o seguinte comando:
```
mvn spring-boot:run
```
Isso iniciará o servidor em `http://localhost:8080`.

## Endpoints
A API possui os seguintes endpoints:
```
POST /financial-transfers: cria uma nova transferência financeira
GET /financial-transfers/{ID}: recupera uma transferência financeira de acordo com o ID
GET /financial-transfers: recupera uma lista de todas as transferências financeiras
```
## Estratégias
O projeto utiliza as seguintes estratégias:

+ Programação orientada a objetos: o código está organizado em classes e segue os princípios da programação orientada a objetos.
+ Injeção de dependência: o projeto usa a injeção de dependência do Spring Boot para gerenciar o ciclo de vida dos objetos.
+ Tratamento de exceção: o projeto usa classes de exceção personalizadas para lidar com erros e retornar mensagens de erro apropriadas ao cliente.
+ Teste de unidade: o projeto usa JUnit e Mockito para teste de unidade para garantir que o código esteja funcionando conforme o esperado.
+ RESTful API: o projeto segue os princípios do design de API RESTful, incluindo o uso de verbos HTTP e endpoints de recursos.

## Design Patterns
O projeto utilizou os seguintes padrões:

+ Model-View-Controller (MVC) Pattern
+ Repository Pattern
+ Dependency Injection Pattern
+ DTO Pattern
+ Exception Handling Pattern

## Contribuidor/Desenvolvedor
Dionisio Lamim Moraes

## Informações adicionais:

As contas bancárias são criadas automaticamente com build do projeto.

Para postar uma nova transferência financeira, use o endpoint e o body abaixo:
```
http://localhost:8080/api/financialTransfer
```
``` Json
{
  "senderAccount": "123123",
  "beneficiaryAccount": "456456",
  "amount": 2001.00,
  "type": "c",
  "transferDate": "2023-04-18T08:30:00Z",
  "scheduleDate": "2023-05-19T14:00:00Z"
}
```
