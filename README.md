# 📱 Lista de Contatos - API REST

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-Database-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

API REST para gerenciamento de lista de contatos desenvolvida com Spring Boot seguindo os princípios de Clean Architecture.

## 🎯 Sobre o Projeto

Sistema de gerenciamento de contatos que permite criar, listar, atualizar e deletar contatos com validações de dados e tratamento de erros robusto.

### ✨ Funcionalidades

- ✅ Criar novos contatos
- ✅ Listar todos os contatos
- ✅ Buscar contato por ID
- ✅ Atualizar informações de contato
- ✅ Deletar contatos
- ✅ Validação de dados (nome, telefone único)
- ✅ Tratamento de exceções customizado
- ✅ Persistência em MySQL

## 🏗️ Arquitetura

Este projeto implementa **Clean Architecture** combinada com **Hexagonal Architecture**, organizando o código em camadas bem definidas:

```
📦 com.listacontatos.jonathan
├── 🎨 presentation/        # Controllers REST
├── 💼 application/         # Services, DTOs, Mappers
├── 🎯 domain/             # Entities, Repository Interfaces
└── 🔧 infra/              # Repository Impl, Exception Handlers
```

### 📊 Diagrama de Arquitetura Completo

Para uma análise detalhada da arquitetura, incluindo:
- Mapeamento completo de dependências
- Fluxo de dados detalhado
- Explicação de cada camada e componente
- Diagramas Mermaid.js interativos

**👉 Consulte: [ARCHITECTURE.md](./ARCHITECTURE.md)**

## 🚀 Começando

### Pré-requisitos

- Java 21+
- Maven 3.8+
- MySQL 8.0+

### Configuração do Banco de Dados

1. Criar o banco de dados:

```sql
CREATE DATABASE contact_list;
```

2. Configurar credenciais em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/contact_list
spring.datasource.username=root
spring.datasource.password=sua_senha
```

### Instalação e Execução

```bash
# Clonar o repositório
git clone https://github.com/jonathan7gb/Lista-Contatos-SpringBoot.git

# Navegar para o diretório
cd Lista-Contatos-SpringBoot

# Executar com Maven
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

## 📚 Endpoints da API

### Criar Contato
```http
POST /contacts
Content-Type: application/json

{
  "name": "João Silva",
  "phoneNumber": "5511999887766"
}
```

### Listar Todos os Contatos
```http
GET /contacts
```

### Buscar Contato por ID
```http
GET /contacts/{id}
```

### Atualizar Contato
```http
PUT /contacts/{id}
Content-Type: application/json

{
  "name": "João Silva Santos",
  "phoneNumber": "5511999887766"
}
```

### Deletar Contato
```http
DELETE /contacts/{id}
```

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Descrição |
|-----------|-----------|
| **Spring Boot 4.0.2** | Framework principal |
| **Spring Web MVC** | REST API endpoints |
| **Spring Data JPA** | Persistência de dados |
| **Hibernate** | ORM |
| **MySQL** | Banco de dados |
| **Bean Validation** | Validação de dados |
| **Lombok** | Redução de boilerplate |
| **Maven** | Gerenciamento de dependências |

## 📋 Regras de Validação

- ✅ Nome obrigatório (3-35 caracteres)
- ✅ Telefone obrigatório (15 caracteres exatos)
- ✅ Telefone deve ser único
- ✅ Dados não podem ser nulos ou vazios

## 🔍 Estrutura do Projeto

```
Lista-Contatos-SpringBoot/
├── src/
│   ├── main/
│   │   ├── java/com/listacontatos/jonathan/
│   │   │   ├── presentation/controller/    # REST Controllers
│   │   │   ├── application/                # Services, DTOs, Mappers
│   │   │   │   ├── service/
│   │   │   │   ├── dto/
│   │   │   │   └── mapper/
│   │   │   ├── domain/                     # Entities, Interfaces
│   │   │   │   ├── entity/
│   │   │   │   └── repository/
│   │   │   └── infra/                      # Infrastructure
│   │   │       ├── persistence/
│   │   │       └── exceptions/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
├── README.md
└── ARCHITECTURE.md                          # 📊 Documentação Detalhada
```

## 🧪 Testes

```bash
# Executar testes
./mvnw test

# Executar testes com cobertura
./mvnw clean verify
```

## 📖 Documentação

- **[ARCHITECTURE.md](./ARCHITECTURE.md)** - Análise completa da arquitetura com diagramas Mermaid.js
- **API Endpoints** - Documentação inline neste README

## 🤝 Contribuindo

Contribuições são bem-vindas! Por favor:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## 👨‍💻 Autor

**Jonathan**
- GitHub: [@jonathan7gb](https://github.com/jonathan7gb)

## 🙏 Agradecimentos

- Spring Boot Team
- Comunidade Java
- Contribuidores do projeto

---

⭐ Se este projeto foi útil para você, considere dar uma estrela!
