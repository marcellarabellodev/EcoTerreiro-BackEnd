# 🧪 Testes de API - EcoTerreiro Backend

Este documento detalha os Casos de Teste executados na API RESTful do EcoTerreiro para garantir a integridade das operações CRUD, a precisão das buscas e o comportamento correto no tratamento de exceções.

**Ferramenta de Execução:** Postman.
**Status Atual:** Todos os Casos de Teste executados e aprovados (Pass).

## Cenários de Teste

Os testes foram organizados em três categorias principais:

1.  **Sucesso (Status 2xx):** Criação, Leitura, Atualização e Deleção bem-sucedidas.
2.  **Busca Vazia (Status 404):** Teste de busca por ID e nomes inexistentes.
3.  **Validação de Dados (Status 400):** Teste de envio de campos nulos ou vazios.

## Tabela de Casos de Teste

| ID do Teste           | Funcionalidade           | Pré-condições | Passos do Teste | Nível de Dificuldade | Resultado Esperado |
|:----------------------|:-------------------------| :--- | :--- | :--- | :--- |
| **TC-POST-001**       | **Criação**              | Servidor e DB acessíveis. | Enviar requisição **POST** para `/api/terreiros` com todos os campos válidos. | Fácil | Status **201 Created**. Retorna o objeto cadastrado com ID. |
| **TC-GET-001**        | **Busca por ID**         | Um Terreiro existe (ID: 1). | Enviar requisição **GET** para `/api/terreiros/1`. | Fácil | Status **200 OK**. Retorna o objeto do Terreiro. |
| **TC-PUT-001**        | **Atualização**          | Um Terreiro existe (ID: 1). | Enviar requisição **PUT** para `/api/terreiros/1` com dados alterados. | Médio | Status **200 OK**. Retorna o objeto atualizado. |
| **TC-DEL-001**        | **Deletar**              | Um Terreiro existe (ID: 2). | Enviar requisição **DELETE** para `/api/terreiros/2`. | Fácil | Status **204 No Content**. Busca subsequente por ID 2 retorna 404. |
| **TC-GET-404-ID**     | **Busca ID inexistente** | Servidor e DB acessíveis. | Enviar requisição **GET** para `/api/terreiros/999` (ID inexistente). | Fácil | Status **404 Not Found**. Corpo JSON com a mensagem: "Terreiro não encontrado com o ID: 999". |
| **TC-GET-404-NAME**   | **Busca Nome Vazio**     | Servidor e DB acessíveis. | Enviar requisição **GET** para `/api/terreiros/byName?nome=NomeInexistente` (ou qualquer outro método de busca). | Fácil | Status **404 Not Found**. Corpo JSON com a mensagem: "Nenhum terreiro encontrado com o nome: NomeInexistente". |
| **TC-PUT-404**        | **Atualização Inválida** | Servidor e DB acessíveis. | Enviar requisição **PUT** para `/api/terreiros/999` (ID inexistente). | Fácil | Status **404 Not Found**. Corpo JSON com a mensagem: "Terreiro não encontrado com o ID: 999". |
| **TC-DEL-404**        | **Erro ao Deletar**      | Servidor e DB acessíveis. | Enviar requisição **DELETE** para `/api/terreiros/999` (ID inexistente). | Fácil | Status **404 Not Found**. Corpo JSON com a mensagem: "Terreiro não encontrado com o ID: 999". |
| **TC-POST-400-NULL**  | **Validação (POST)**     | Servidor e DB acessíveis. | Enviar requisição **POST** com campo obrigatório (`nomeTerreiro`) **omitido**. | Fácil | Status **400 Bad Request**. Corpo JSON detalhando o erro de validação. |
| **TC-POST-400-EMPTY** | **Validação (POST)**     | Servidor e DB acessíveis. | Enviar requisição **POST** com campo obrigatório (`endTerreiro`) como string **vazia** (`""`). | Fácil | Status **400 Bad Request**. Corpo JSON detalhando o erro de validação. |


### Caso queira, você poderá acessar a planilha de Casos de Teste [Casos-de-teste.xls](./Casos-de-teste.xls)