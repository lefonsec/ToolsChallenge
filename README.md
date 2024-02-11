## Descrição

Este é um exemplo simples de aplicação para gerenciar transações financeiras.

## Endpoints

### Criar uma nova transação
- **Método:** POST
- **Endpoint:** `http://localhost:8080/api/pagamento`
- **Descrição:** Cria uma nova transação de pagamento.
- **Corpo da Requisição:** JSON no formato:
```json
{
    "transacao": {
        "cartao": "428773****1232345",
        "id": "34",
        "descricao": {
            "valor": 15,
            "dataHora": "01/05/2023 18:30:00",
            "estabelecimento": "PetShop Mundo Cão"
        },
        "formaPagamento": {
            "tipo": "PARCELADO_EMISSOR",
            "parcela": "2"
        }
    }
}
```
### Obter todas as transações
- **Método:** GET
- **Endpoint:** `http://localhost:8080/api/transacoes`
- **Descrição:** Retorna todas as transações registradas.

### Obter transações por ID
- **Método:** GET
- **Endpoint:** `http://localhost:8080/api/consulta/{id}`
- **Descrição:** Retorna as transações registradas para um id específico.

### Solicitar estorno de transação
- **Método:** POST
- **Endpoint:** `http://localhost:8080/api/estorno?id=?`
- **Parâmetros da Requisição:** `id` - ID da transação a ser estornada
- **Descrição:** Solicita o estorno de uma transação específica.

## Executando a aplicação
1. Clone este repositório.
2. Abra o projeto em sua IDE preferida.
3. Execute a aplicação.
4. Utilize as URLs fornecidas acima para interagir com os endpoints da API.

## Tecnologias Utilizadas
- Java
- Spring Boot
- RESTful API
- JSON
- JUnit
- Padrão de projetos -> DTO,Padrão de Projeto Service
- JPA
- Git/Github