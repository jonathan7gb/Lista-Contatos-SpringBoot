# Arquitetura do Sistema - Lista de Contatos API REST

## 📋 Índice
1. [Visão Geral](#visão-geral)
2. [Diagrama de Arquitetura](#diagrama-de-arquitetura)
3. [Padrão Arquitetural](#padrão-arquitetural)
4. [Camadas da Aplicação](#camadas-da-aplicação)
5. [Fluxo de Dados](#fluxo-de-dados)
6. [Dependências e Tecnologias](#dependências-e-tecnologias)

## 🔍 Visão Geral

Esta aplicação implementa uma API REST para gerenciamento de lista de contatos usando **Spring Boot 4.0.2** e **Java 21**. A arquitetura segue os princípios de **Clean Architecture** (Arquitetura Limpa) combinada com **Hexagonal Architecture**, organizando o código em camadas bem definidas com separação clara de responsabilidades.

## 📊 Diagrama de Arquitetura

```mermaid
graph LR
    subgraph "Cliente Externo"
        Client[Cliente HTTP/REST]
    end

    subgraph "Presentation Layer"
        Controller[ContactController<br/>@RestController<br/>Endpoints REST]
    end

    subgraph "Application Layer"
        Service[ContactService<br/>@Service<br/>Regras de Negócio]
        DTORequest[ContactRequestDTO<br/>Record]
        DTOResponse[ContactResponseDTO<br/>Record]
        Mapper[ContactMapper<br/>@Component<br/>Conversão DTO ↔ Entity]
    end

    subgraph "Domain Layer"
        Entity[Contact<br/>@Entity<br/>Entidade de Negócio]
        Repository[ContactRepository<br/>Interface<br/>Contrato Repositório]
    end

    subgraph "Infrastructure Layer"
        RepositoryImpl[ContactRepositoryImpl<br/>@Repository<br/>JpaRepository]
        ExceptionHandler[GlobalExceptionHandler<br/>@RestControllerAdvice<br/>Tratamento de Erros]
        Exceptions[Exceções Customizadas<br/>ContactNotFound<br/>PhoneNumberAlreadyExists<br/>InvalidPhoneNumber<br/>ContactDataIsNull]
    end

    subgraph "Persistence Layer"
        DB[(MySQL Database<br/>contact_list)]
        JPA[Spring Data JPA<br/>Hibernate]
    end

    subgraph "Cross-Cutting Concerns"
        Validation[Bean Validation<br/>@NotNull, @Size<br/>Jakarta Validation]
        Lombok[Lombok<br/>@Data, @AllArgsConstructor<br/>Redução Boilerplate]
    end

    Client -->|HTTP Request<br/>GET/POST/PUT/DELETE| Controller
    Controller -->|Chama métodos| Service
    Controller -->|Recebe/Retorna| DTORequest
    Controller -->|Retorna| DTOResponse
    
    Service -->|Usa| Mapper
    Service -->|Valida e processa| DTORequest
    Service -->|Retorna| DTOResponse
    Service -->|Acessa| RepositoryImpl
    Service -->|Lança| Exceptions
    
    Mapper -->|Converte| DTORequest
    Mapper -->|Para| Entity
    Mapper -->|Converte| Entity
    Mapper -->|Para| DTOResponse
    
    RepositoryImpl -->|Implementa| Repository
    RepositoryImpl -->|Estende| JPA
    RepositoryImpl -->|Persiste| Entity
    
    JPA -->|Conecta| DB
    
    ExceptionHandler -->|Captura| Exceptions
    ExceptionHandler -->|Retorna| Client
    
    Entity -.->|Usa| Validation
    Entity -.->|Usa| Lombok
    Service -.->|Valida com| Validation
    
    Controller -->|HTTP Response<br/>JSON| Client

    style Client fill:#e1f5ff
    style Controller fill:#fff4e6
    style Service fill:#e8f5e9
    style Entity fill:#f3e5f5
    style RepositoryImpl fill:#fce4ec
    style DB fill:#ffebee
    style ExceptionHandler fill:#fff3e0
    style Validation fill:#f1f8e9
    style Lombok fill:#f1f8e9
```

## 🏗️ Padrão Arquitetural

### Clean Architecture + Hexagonal Architecture

A aplicação adota **Clean Architecture** (proposta por Robert C. Martin) combinada com princípios de **Hexagonal Architecture** (Ports & Adapters), resultando em:

#### Vantagens desta Arquitetura:

1. **Separação de Responsabilidades**: Cada camada tem um propósito bem definido
2. **Independência de Frameworks**: O domínio não depende do Spring ou JPA
3. **Testabilidade**: Cada camada pode ser testada independentemente
4. **Manutenibilidade**: Mudanças em uma camada têm impacto mínimo em outras
5. **Regra de Dependência**: As dependências apontam sempre para dentro (para o domínio)

#### Princípios Aplicados:

- **Dependency Inversion Principle (DIP)**: Camadas superiores não dependem de implementações concretas
- **Single Responsibility Principle (SRP)**: Cada classe tem uma única razão para mudar
- **Interface Segregation**: Interfaces específicas para cada necessidade

## 📦 Camadas da Aplicação

### 1️⃣ Presentation Layer (Apresentação)
**Pacote**: `com.listacontatos.jonathan.presentation`

**Responsabilidade**: Interface com o mundo externo através de REST API

**Componentes**:
- `ContactController`: Gerencia endpoints HTTP (GET, POST, PUT, DELETE)
- Recebe requisições HTTP e delega para a camada de aplicação
- Retorna respostas HTTP com status codes apropriados

**Tecnologias**: Spring Web MVC, REST annotations

---

### 2️⃣ Application Layer (Aplicação)
**Pacote**: `com.listacontatos.jonathan.application`

**Responsabilidade**: Orquestração de casos de uso e regras de negócio

**Componentes**:
- `ContactService`: Implementa a lógica de negócio (validações, regras)
- `ContactRequestDTO`: Estrutura de dados para entrada
- `ContactResponseDTO`: Estrutura de dados para saída
- `ContactMapper`: Conversão entre DTOs e Entidades

**Validações Implementadas**:
- Nome e telefone não podem ser nulos ou vazios
- Número de telefone deve ter no máximo 15 caracteres
- Número de telefone deve ser único
- Verificação de existência antes de atualização

---

### 3️⃣ Domain Layer (Domínio)
**Pacote**: `com.listacontatos.jonathan.domain`

**Responsabilidade**: Núcleo do negócio, independente de tecnologias externas

**Componentes**:
- `Contact`: Entidade de domínio representando um contato
  - `id`: Identificador único
  - `name`: Nome do contato (3-35 caracteres)
  - `phoneNumber`: Telefone (15 caracteres, único)
- `ContactRepository`: Interface de contrato (porta) para persistência

**Regras de Domínio**:
- Bean Validation annotations (`@NotNull`, `@Size`)
- Constraints de banco de dados (nullable, unique, length)

---

### 4️⃣ Infrastructure Layer (Infraestrutura)
**Pacote**: `com.listacontatos.jonathan.infra`

**Responsabilidade**: Implementação de detalhes técnicos e comunicação externa

**Componentes**:

#### Persistence (`infra.persistence`):
- `ContactRepositoryImpl`: Adaptador que implementa a interface do domínio
  - Estende `JpaRepository` do Spring Data
  - Implementa métodos customizados de consulta

#### Exceptions (`infra.exceptions`):
- `GlobalExceptionHandler`: Tratamento centralizado de exceções
- Exceções customizadas:
  - `ContactNotFound`: Contato não encontrado (404)
  - `ContactDataIsNull`: Dados nulos ou inválidos (400)
  - `InvalidPhoneNumber`: Formato de telefone inválido (400)
  - `PhoneNumberAlreadyExists`: Telefone já cadastrado (409)

---

## 🔄 Fluxo de Dados

### Exemplo: Criar um Novo Contato (POST /contacts)

```
1. Cliente HTTP envia POST /contacts com JSON:
   {
     "name": "João Silva",
     "phoneNumber": "5511999887766"
   }

2. ContactController recebe a requisição
   ↓
   Converte JSON para ContactRequestDTO
   ↓
   Chama contactService.save(requestDTO)

3. ContactService valida os dados:
   ✓ Nome não é nulo/vazio?
   ✓ Telefone não é nulo/vazio?
   ✓ Telefone já existe? (consulta RepositoryImpl)
   ✓ Telefone tem até 15 caracteres?
   
   Se inválido → Lança exceção customizada
   ↓
   Usa ContactMapper para converter DTO → Entity
   ↓
   Chama repositoryImpl.save(contact)

4. ContactRepositoryImpl (Infraestrutura)
   ↓
   Spring Data JPA processa a entidade
   ↓
   Hibernate gera SQL INSERT
   ↓
   MySQL persiste no banco de dados

5. Retorno:
   Entity salva → Mapper converte para ContactResponseDTO
   ↓
   Controller retorna HTTP 201 CREATED

6. Em caso de erro:
   Exceção é lançada
   ↓
   GlobalExceptionHandler captura
   ↓
   Retorna HTTP com status e mensagem apropriados
```

### Exemplo: Buscar Todos os Contatos (GET /contacts)

```
1. Cliente HTTP envia GET /contacts

2. ContactController.findAll()
   ↓
   Chama contactService.findAll()

3. ContactService
   ↓
   Chama repositoryImpl.findAll()
   ↓
   Recebe List<Contact>
   ↓
   Verifica se lista está vazia (lança ContactNotFound se sim)
   ↓
   Converte cada Contact para ContactResponseDTO usando Mapper
   ↓
   Retorna List<ContactResponseDTO>

4. Controller retorna HTTP 200 OK com JSON array

5. JPA/Hibernate:
   ↓
   Executa SELECT * FROM contact
   ↓
   MySQL retorna os registros
   ↓
   Hibernate mapeia para entidades Contact
```

## 🔧 Dependências e Tecnologias

### Stack Tecnológico

| Camada | Tecnologia | Versão | Propósito |
|--------|-----------|--------|-----------|
| **Framework** | Spring Boot | 4.0.2 | Framework principal |
| **Linguagem** | Java | 21 | Linguagem de programação |
| **Web** | Spring Web MVC | - | REST API endpoints |
| **Persistência** | Spring Data JPA | - | Abstração de acesso a dados |
| **ORM** | Hibernate | - | Mapeamento objeto-relacional |
| **Banco de Dados** | MySQL | - | Armazenamento persistente |
| **Validação** | Bean Validation | Jakarta | Validação de dados |
| **Mapeamento** | Lombok | - | Redução de boilerplate code |
| **Build** | Maven | - | Gerenciamento de dependências |

### Configuração de Banco de Dados

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/contact_list
spring.datasource.username=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

### Estrutura de Banco de Dados

```sql
CREATE TABLE contact (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE
);
```

## 🎯 Por que esta Estrutura de Diagrama?

### Escolhas de Design do Diagrama

1. **Organização em Subgraphs**: 
   - Cada camada é claramente isolada visualmente
   - Facilita a compreensão da separação de responsabilidades
   - Mostra a hierarquia e dependências entre camadas

2. **Direção Horizontal (graph LR)**:
   - Fluxo natural de leitura (esquerda → direita)
   - Representa o fluxo de dados do cliente até o banco
   - Mais intuitivo para mostrar pipeline de processamento

3. **Cores Diferenciadas**:
   - Cada camada tem sua cor para fácil identificação
   - Cliente (azul claro): Origem das requisições
   - Presentation (laranja): Ponto de entrada da API
   - Application (verde): Lógica de negócio
   - Domain (roxo): Núcleo do sistema
   - Infrastructure (rosa): Detalhes técnicos
   - Database (vermelho claro): Persistência
   - Cross-cutting (verde claro): Aspectos transversais

4. **Setas com Labels**:
   - Mostram a direção do fluxo de dados
   - Indicam o tipo de comunicação (HTTP, método calls)
   - Diferenciam dependências fortes (→) de fracas (-.->)

5. **Anotações nos Componentes**:
   - Incluem annotations do Spring (@RestController, @Service)
   - Mostram tecnologias utilizadas (JpaRepository, Record)
   - Facilitam localização no código-fonte

6. **Separação de Cross-Cutting Concerns**:
   - Validation e Lombok são mostrados separadamente
   - Indicam aspectos que atravessam múltiplas camadas
   - Representados com linhas pontilhadas (não-invasivos)

### Benefícios desta Representação

✅ **Visão Completa**: Mostra toda a arquitetura em um único diagrama  
✅ **Rastreabilidade**: Fácil mapear componentes do diagrama para código  
✅ **Onboarding**: Novos desenvolvedores entendem rapidamente a estrutura  
✅ **Documentação Viva**: Pode ser atualizado conforme o código evolui  
✅ **Decisões Arquiteturais**: Evidencia a separação de camadas e princípios SOLID  

## 🚀 Como Executar

```bash
# Clonar o repositório
git clone https://github.com/jonathan7gb/Lista-Contatos-SpringBoot.git

# Navegar para o diretório
cd Lista-Contatos-SpringBoot

# Configurar MySQL (criar database)
mysql -u root -p
CREATE DATABASE contact_list;

# Executar a aplicação
./mvnw spring-boot:run

# API estará disponível em:
http://localhost:8080/contacts
```

## 📚 Endpoints da API

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/contacts` | Criar novo contato |
| GET | `/contacts` | Listar todos os contatos |
| GET | `/contacts/{id}` | Buscar contato por ID |
| PUT | `/contacts/{id}` | Atualizar contato |
| DELETE | `/contacts/{id}` | Deletar contato |

## 📝 Notas de Implementação

### Melhorias Futuras Sugeridas

1. **Segurança**: Adicionar Spring Security para autenticação/autorização
2. **Paginação**: Implementar paginação no endpoint GET /contacts
3. **Testes**: Ampliar cobertura de testes unitários e de integração
4. **Cache**: Adicionar cache para queries frequentes (Spring Cache)
5. **API Documentation**: Integrar Swagger/OpenAPI para documentação automática
6. **Logging**: Implementar logging estruturado com SLF4J/Logback
7. **Métricas**: Adicionar Spring Actuator para monitoramento
8. **Containerização**: Adicionar Dockerfile para deployment

### Padrões de Código Observados

- ✅ Uso de Records do Java para DTOs imutáveis
- ✅ Injeção de dependência via @Autowired
- ✅ Tratamento centralizado de exceções
- ✅ Validação em múltiplas camadas
- ✅ Nomenclatura clara e consistente

---

**Documentação gerada em**: 2026-02-16  
**Versão da Aplicação**: 0.0.1-SNAPSHOT  
**Autor**: jonathan7gb
