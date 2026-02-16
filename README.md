# Micronaut Service Scheduling REST API

API REST para agendamento de serviços construída com Micronaut Framework.

## 🚀 Tecnologias

- **Micronaut 4.10.6** - Framework Java
- **MySQL 8.0** - Banco de dados
- **Micronaut Data JDBC** - Persistência de dados
- **JWT** - Autenticação
- **JUnit 5** - Testes

## 📋 Pré-requisitos

- Java 17 ou superior
- Docker (para MySQL)
- Maven (incluído via wrapper)

## 🔧 Configuração

### 1. Subir o banco de dados MySQL

```bash
docker run --name trabalhoSD -e MYSQL_ROOT_PASSWORD=trabalhoSD -p 3306:3306 -d -v mysqlvolume:/var/lib/mysql mysql:8.0
```

### 2. Criar a database

```bash
docker exec -it trabalhoSD mysql -u root -ptrabalhoSD -e "CREATE DATABASE service_db;"
```

### 3. Compilar o projeto

```bash
.\mvnw.bat clean compile
```

### 4. Executar a aplicação

```bash
.\mvnw.bat mn:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 🧪 Testes

Executar todos os testes:

```bash
.\mvnw.bat clean test
```

## 📚 Endpoints Principais

### Requesters (Solicitantes)

- `GET /requesters` - Listar todos os solicitantes (paginado com filtros)
- `GET /requesters/{id}` - Buscar solicitante por ID
- `GET /requesters/{id}/services` - Listar serviços de um solicitante
- `POST /requesters` - Criar novo solicitante (requer autenticação)
- `PATCH /requesters/{id}` - Atualizar solicitante (requer autenticação)
- `DELETE /requesters/{id}` - Remover solicitante (requer autenticação)

### Services (Serviços)

- `GET /services` - Listar todos os serviços (paginado com filtros)
- `GET /services/{id}` - Buscar serviço por ID
- `POST /services` - Criar novo serviço (requer autenticação)
- `PATCH /services/{id}` - Atualizar serviço (requer autenticação)
- `DELETE /services/{id}` - Remover serviço (requer autenticação)

## 🔐 Autenticação

A API usa JWT (JSON Web Tokens) para autenticação. Para acessar endpoints protegidos:

1. Faça login no endpoint `/login` com as credenciais:
   - **Usuário**: `usuario`
   - **Senha**: `senha123`

2. Use o token JWT retornado no header `Authorization: Bearer <token>`

Endpoints públicos (sem autenticação):
- `GET /requesters`
- `GET /requesters/{id}`
- `GET /services`
- `GET /services/{id}`
- `GET /requesters/{id}/services`

Endpoints protegidos (requerem autenticação JWT):
- Todos os métodos `POST`, `PATCH` e `DELETE`

## 📊 Modelo de Dados

### Requester

```json
{
  "id": 1,
  "fullName": "João Silva",
  "email": "joao@example.com",
  "department": "TI",
  "userName": "joao_silva",
  "phoneNumber": "11999999999"
}
```

### Service

```json
{
  "id": 1,
  "description": "Manutenção de servidor",
  "type": "Técnico",
  "value": 500.00,
  "scheduledFor": "2026-02-20",
  "openedAt": "2026-02-15",
  "requester": {
    "id": 1,
    "fullName": "João Silva"
  }
}
```

## ⚙️ Configuração do Banco de Dados

As configurações do banco de dados estão em `src/main/resources/application.properties`:

```properties
datasources.default.url=jdbc:mysql://localhost:3306/service_db?allowPublicKeyRetrieval=true&useSSL=false
datasources.default.username=root
datasources.default.password=trabalhoSD
datasources.default.schema-generate=CREATE
```

## 📝 Notas

- `schema-generate=CREATE`: As tabelas são criadas se não existirem (dados são mantidos entre reinícios)
- Para produção, use `schema-generate=NONE` e gerencie o schema manualmente
- O relacionamento entre Service e Requester é configurado via foreign key no banco
- A API usa JWT para autenticação com tokens de acesso que expiram em 30 minutos

## 🤝 Contribuindo

1. Crie uma branch para sua feature
2. Faça commit das suas mudanças
3. Push para a branch
4. Abra um Pull Request

## 📄 Licença

Este projeto é parte de um trabalho acadêmico.

---

## Micronaut 4.10.6 Documentation

- [User Guide](https://docs.micronaut.io/4.10.6/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.10.6/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.10.6/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Micronaut Maven Plugin documentation](https://micronaut-projects.github.io/micronaut-maven-plugin/latest/)
## Feature maven-enforcer-plugin documentation

- [https://maven.apache.org/enforcer/maven-enforcer-plugin/](https://maven.apache.org/enforcer/maven-enforcer-plugin/)


## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)


## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)
