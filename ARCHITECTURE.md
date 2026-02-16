# Arquitetura - Lista de Contatos API REST

## 🔍 Visão Geral

API REST para gerenciamento de contatos implementada com **Spring Boot 4.0.2** e **Java 21**, seguindo os princípios de **Clean Architecture** e **Hexagonal Architecture**.

## 📊 Diagrama de Arquitetura

![Diagrama de Arquitetura](docs/images/architecture.png)

O diagrama acima ilustra a arquitetura completa da aplicação, mostrando todas as camadas e suas interações:

- **Cliente Externo**: Interface HTTP/REST para comunicação
- **Presentation Layer**: Controllers REST que recebem requisições
- **Application Layer**: Serviços com lógica de negócio e conversão de dados
- **Domain Layer**: Entidades e interfaces de repositório (núcleo do negócio)
- **Infrastructure Layer**: Implementações concretas e tratamento de exceções
- **Persistence Layer**: Integração com MySQL via Spring Data JPA
- **Cross-Cutting Concerns**: Validação e redução de boilerplate (Lombok)

## 🏗️ Padrão Arquitetural

### Clean Architecture + Hexagonal Architecture

A aplicação combina **Clean Architecture** (Robert C. Martin) com princípios de **Hexagonal Architecture** (Ports & Adapters).

#### Vantagens

1. **Separação de Responsabilidades** - Cada camada tem propósito bem definido
2. **Independência de Frameworks** - O domínio não depende do Spring ou JPA
3. **Testabilidade** - Camadas testáveis independentemente
4. **Manutenibilidade** - Mudanças em uma camada têm impacto mínimo em outras
5. **Regra de Dependência** - Dependências apontam para o domínio (centro)

#### Princípios SOLID Aplicados

- **Dependency Inversion Principle (DIP)** - Camadas superiores dependem de abstrações
- **Single Responsibility Principle (SRP)** - Cada classe tem uma única responsabilidade
- **Interface Segregation** - Interfaces específicas para cada necessidade

## 📦 Camadas da Aplicação

### 1. Presentation Layer
**Pacote:** `com.listacontatos.jonathan.presentation`

**Responsabilidade:** Interface REST com o mundo externo

**Componentes:**
- `ContactController` - Endpoints HTTP (GET, POST, PUT, DELETE)

---

### 2. Application Layer
**Pacote:** `com.listacontatos.jonathan.application`

**Responsabilidade:** Orquestração de casos de uso e regras de negócio

**Componentes:**
- `ContactService` - Lógica de negócio e validações
- `ContactRequestDTO` - Dados de entrada
- `ContactResponseDTO` - Dados de saída  
- `ContactMapper` - Conversão DTO ↔ Entity

**Validações:**
- Nome e telefone não podem ser nulos/vazios
- Telefone deve ter até 15 caracteres
- Telefone deve ser único
- Verificação de existência antes de atualização

---

### 3. Domain Layer
**Pacote:** `com.listacontatos.jonathan.domain`

**Responsabilidade:** Núcleo do negócio (independente de tecnologias)

**Componentes:**
- `Contact` - Entidade de domínio
  - `id` - Identificador único
  - `name` - Nome do contato (3-35 caracteres)
  - `phoneNumber` - Telefone (até 15 caracteres, único)
- `ContactRepository` - Interface (porta) para persistência

---

### 4. Infrastructure Layer
**Pacote:** `com.listacontatos.jonathan.infra`

**Responsabilidade:** Implementações técnicas e comunicação externa

**Componentes:**
- `ContactRepositoryImpl` - Adaptador JPA
- `GlobalExceptionHandler` - Tratamento centralizado
- Exceções customizadas:
  - `ContactNotFound` (404)
  - `ContactDataIsNull` (400)
  - `InvalidPhoneNumber` (400)
  - `PhoneNumberAlreadyExists` (409)

## 🔄 Fluxo de Dados

### Criar Contato (POST /contacts)

```
1. Cliente envia POST com JSON → ContactController

2. Controller converte JSON → ContactRequestDTO
   ↓
   Chama contactService.save(requestDTO)

3. ContactService valida:
   ✓ Nome não nulo/vazio
   ✓ Telefone não nulo/vazio
   ✓ Telefone único (consulta repositório)
   ✓ Telefone até 15 caracteres
   
   Usa ContactMapper: DTO → Entity
   ↓
   Chama repositoryImpl.save(contact)

4. ContactRepositoryImpl
   ↓
   Spring Data JPA → Hibernate → SQL INSERT → MySQL

5. Retorno:
   Entity → Mapper → ContactResponseDTO
   ↓
   Controller → HTTP 201 CREATED

6. Em caso de erro:
   Exceção → GlobalExceptionHandler → HTTP com status apropriado
```

### Listar Contatos (GET /contacts)

```
1. Cliente envia GET → ContactController

2. Controller chama contactService.findAll()

3. ContactService
   ↓
   Chama repositoryImpl.findAll()
   ↓
   Verifica se lista vazia (lança ContactNotFound)
   ↓
   Converte cada Contact → ContactResponseDTO
   ↓
   Retorna List<ContactResponseDTO>

4. JPA/Hibernate:
   SELECT * FROM contact → MySQL
   ↓
   Mapeia para List<Contact>

5. Controller → HTTP 200 OK com JSON array
```

## 🔧 Stack Tecnológico

| Camada | Tecnologia | Propósito |
|--------|-----------|-----------|
| **Framework** | Spring Boot 4.0.2 | Framework principal |
| **Linguagem** | Java 21 | Linguagem de programação |
| **Web** | Spring Web MVC | REST API endpoints |
| **Persistência** | Spring Data JPA | Abstração de acesso a dados |
| **ORM** | Hibernate | Mapeamento objeto-relacional |
| **Banco de Dados** | MySQL | Armazenamento persistente |
| **Validação** | Bean Validation (Jakarta) | Validação de dados |
| **Utilitários** | Lombok | Redução de código boilerplate |
| **Build** | Maven | Gerenciamento de dependências |

## 📊 Estrutura de Banco de Dados

```sql
CREATE TABLE contact (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE
);
```

## 📚 Endpoints da API

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| POST | `/contacts` | Criar novo contato | 201 Created |
| GET | `/contacts` | Listar todos os contatos | 200 OK |
| GET | `/contacts/{id}` | Buscar contato por ID | 200 OK |
| PUT | `/contacts/{id}` | Atualizar contato | 200 OK |
| DELETE | `/contacts/{id}` | Deletar contato | 204 No Content |

### Códigos de Status HTTP

- **200 OK** - Requisição bem-sucedida
- **201 Created** - Recurso criado com sucesso
- **204 No Content** - Recurso deletado com sucesso
- **400 Bad Request** - Dados inválidos
- **404 Not Found** - Recurso não encontrado
- **409 Conflict** - Telefone já existe

## 🎯 Benefícios da Arquitetura

✅ **Visão Completa** - Toda a arquitetura em um único diagrama  
✅ **Rastreabilidade** - Fácil mapear componentes do diagrama para código  
✅ **Onboarding** - Novos desenvolvedores entendem rapidamente  
✅ **Manutenibilidade** - Mudanças isoladas por camada  
✅ **Testabilidade** - Cada camada testável independentemente

## 🚀 Executando o Projeto

```bash
# Clonar
git clone https://github.com/jonathan7gb/Lista-Contatos-SpringBoot.git
cd Lista-Contatos-SpringBoot

# Configurar MySQL
mysql -u root -p
CREATE DATABASE contact_list;

# Configurar credenciais em application.properties

# Executar
./mvnw spring-boot:run
```

---

**Autor:** jonathan7gb  
**Versão:** 0.0.1-SNAPSHOT
