
# 🌿 EcoTerreiro - Backend API (Spring Boot)

### Olá a todos e todas! Sejam bem vindos(as) a parte Back-end do meu projeto EcoTerreiro. 🥰

Este repositório contém o código-fonte da API RESTful do projeto EcoTerreiro, desenvolvido em **Java** com o *framework* **Spring Boot**. A API é responsável pela gestão de dados dos Terreiros cadastrados, incluindo informações de contato, práticas e dificuldades.

____________________________

## 💻 Tecnologias Utilizadas

| Tecnologia | Versão Principal | Descrição |
| :--- | :--- | :--- |
| **Linguagem** | Java (JDK 17+) | Linguagem principal do projeto. |
| **Framework** | Spring Boot | Utilizado para inicializar e configurar a aplicação. |
| **Banco de Dados** | MySQL | Sistema de gerenciamento de banco de dados (SGBD) para persistência de dados. |
| **Persistência** | Spring Data JPA / Hibernate | Mapeamento Objeto-Relacional (ORM) para comunicação com o MySQL. |
| **Ferramenta de Build** | Maven | Gerenciamento de dependências e construção do projeto. |
| **Validação** | Bean Validation | Utilizado para validar dados de entrada (JSON) antes de serem enviados ao banco. |
| **Containerização** | Docker | Utilizado para empacotar e isolar a aplicação e suas dependências, garantindo um ambiente consistente. |

____________________________

## ⚙️ Endpoints da API

A base de todos os endpoints é `/api/terreiros`.

| Método | Endpoint | Descrição | Parâmetros |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/terreiros` | Cadastra um novo Terreiro. | Corpo JSON do objeto `Terreiro` |
| **GET** | `/api/terreiros` | Lista todos os Terreiros cadastrados. | - |
| **GET** | `/api/terreiros/{id}` | Busca um Terreiro específico pelo ID. | `id` (Path Variable) |
| **GET** | `/api/terreiros/byName` | Busca exata por nome do Terreiro. | `?nome={nome}` (Query Param) |
| **GET** | `/api/terreiros/byNomeParcial` | Busca parcial por um trecho do nome do Terreiro. | `?nome={trecho}` (Query Param) |
| **PUT** | `/api/terreiros/{id}` | Atualiza todos os dados de um Terreiro pelo ID. | `id` (Path Variable) + Corpo JSON |
| **DELETE** | `/api/terreiros/{id}` | Remove um Terreiro do banco de dados pelo ID. | `id` (Path Variable) |

____________________________

## 🛡️ Tratamento de Erros e Boas Práticas

A API implementa um **Global Exception Handler** (`@ControllerAdvice`) para garantir que as respostas de erro sejam padronizadas (JSON) e informativas.
* **404 Not Found:** Retornado quando um ID não existe (`TerreiroNotFoundException`).
* **400 Bad Request:** Retornado automaticamente em falhas de validação (`@Valid` e `@NotBlank`) nos dados de entrada.

____________________________

### Espero que gostem e fiquem a vontade de se comunicarem comigo e trazer novas ideias e novos projetos!

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/marcella-rabello-b80b08315/)

[![E-mail](https://img.shields.io/badge/email-EA4335?style=for-the-badge&logo=gmail&logoColor=white)](mailto:marcella.rabello@hotmail.com)

## ✒️Autora

**Marcella Rabello** - *Desenvolvedora Full Stack*
