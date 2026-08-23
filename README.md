# CRM MVP

API REST para um CRM (Customer Relationship Management) construída com Spring Boot. O MVP disponibiliza operações de cadastro e manutenção de usuários e negócios, persistindo os dados no Firebase Firestore.

## Status

O projeto está em desenvolvimento. Os recursos disponíveis atualmente são:

- CRUD de usuários;
- CRUD de negócios;
- validação dos dados de entrada;
- persistência no Firebase Firestore;
- geração de IDs sequenciais por transação no Firestore;
- conversão de documentos Firestore para objetos Java;
- testes automatizados pontuais para o repositório de negócios e carregamento do contexto.

Os diretórios `Clients` e `Dashboard` ainda não possuem funcionalidades implementadas em `src/main/java`.

## Tecnologias

- Java 21;
- Spring Boot 3.4.2;
- Spring Web;
- Spring Validation;
- Spring Security;
- Firebase Admin SDK 9.4.1;
- Google Cloud Firestore;
- JWT (JJWT 0.12.5);
- Lombok;
- Gradle;
- Docker e Docker Compose;
- JUnit 5 e Mockito.

## Arquitetura

O código segue uma arquitetura em camadas organizada por módulo:

```text
src/main/java/com/crm/MVP/
├── infra/
│   └── config/       # Firebase e componentes de infraestrutura
└── modules/
    ├── Business/
    │   ├── Controller/ # Endpoints HTTP
    │   ├── DTO/        # Objetos de entrada e saída da API
    │   ├── Entity/     # Modelo de domínio
    │   ├── Mapper/     # Conversão entre DTOs e entidades
    │   ├── Repository/ # Acesso ao Firestore
    │   └── Service/    # Regras e casos de uso
    └── User/
        ├── Controller/
        ├── DTO/
        ├── Entity/
        ├── Mapper/
        ├── Repository/
        └── Service/
```

O fluxo principal de uma requisição é:

```text
HTTP request -> Controller -> Mapper -> Service -> Repository -> Firestore
HTTP response <- Controller <- Mapper <- Service <- Repository
```

### Padrões identificados

O projeto usa padrões e princípios comuns de aplicações Spring:

- **Layered Architecture (arquitetura em camadas):** separa responsabilidades entre Controller, Service e Repository;
- **Repository:** `BusinessRepository` e `UserRepository` encapsulam o acesso ao Firestore;
- **Data Transfer Object (DTO):** classes `*RequestDTO` e `*ResponseDTO` evitam expor diretamente o modelo usado pela API;
- **Mapper/Assembler:** classes `BusinessMapper` e `UserMapper` convertem DTOs e entidades;
- **Dependency Injection:** dependências são fornecidas pelos construtores e gerenciadas pelo Spring;
- **Facade/Application Service:** as classes `*Service` oferecem uma interface de casos de uso para os controllers;
- **Data Mapper:** os métodos `toDocument` e `fromDocument` traduzem entidades para o formato do Firestore e vice-versa.

## Endpoints

A aplicação utiliza a porta `8081` por padrão.

### Usuários

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/users` | Cria um usuário |
| `GET` | `/users` | Lista usuários |
| `GET` | `/users/{id}` | Busca um usuário por ID |
| `PUT` | `/users/{id}` | Atualiza um usuário |
| `DELETE` | `/users/{id}` | Remove um usuário |

### Negócios

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/businesses` | Cria um negócio |
| `GET` | `/businesses` | Lista negócios |
| `GET` | `/businesses/{id}` | Busca um negócio por ID |
| `PUT` | `/businesses/{id}` | Atualiza um negócio |
| `DELETE` | `/businesses/{id}` | Remove um negócio |

As operações `POST` e `PUT` usam `@Valid` para validar os DTOs de entrada. Erros de entidade inexistente retornam `404 Not Found` por meio de `ResponseStatusException`.

## Modelos

### Usuário

Campos aceitos em `UserRequestDTO`:

- `nome`: obrigatório, até 100 caracteres;
- `email`: obrigatório e válido, até 255 caracteres;
- `admin`: obrigatório;
- `idioma`: obrigatório, até 10 caracteres;
- `fusoHorario`: obrigatório, até 50 caracteres;
- `formatoDeData`: obrigatório, até 20 caracteres;
- `notificacoesEmail`, `alertasWeb` e `resumoSemanal`: obrigatórios.

### Negócio

Campos aceitos em `BusinessRequestDTO`:

- `titulo`: obrigatório, até 100 caracteres;
- `nomeEmpresa`: obrigatório, até 150 caracteres;
- `valorContrato`: obrigatório e maior que zero;
- `data`: obrigatória, até 20 caracteres;
- `estagioDeNegociacao`: obrigatório, até 50 caracteres;
- `clienteId`: opcional.

## Configuração do Firebase

A classe `infra.config.bd` cria o bean `Firestore`. Por segurança, o arquivo de credenciais não deve ser versionado.

Por padrão, a aplicação procura as credenciais em:

```text
./src/main/java/com/crm/MVP/infra/config/firebase/ServiceAccount.json
```

Esse caminho pode ser alterado pela propriedade `firebase.credentials.path`. Configure um arquivo de credenciais válido antes de executar a aplicação, ou forneça essa propriedade por variável/opção de ambiente conforme o ambiente de execução.

As coleções usadas são:

- `users`: usuários;
- `businesses`: negócios;
- `sequences`: contadores usados para gerar IDs sequenciais.

## Segurança

O projeto possui dependências de Spring Security e JJWT, além de classes para leitura e validação de tokens Bearer. No estado atual, porém, não foi identificada uma `SecurityFilterChain`, um endpoint de login ou o registro do filtro JWT no pipeline do Spring Security.

Portanto, a autenticação JWT deve ser considerada **incompleta** e não deve ser tratada como uma proteção funcional dos endpoints até que essa integração seja concluída. A chave secreta também deve ser externalizada para configuração segura antes de um uso em produção.

## Como executar localmente

### Pré-requisitos

- JDK 21;
- acesso a um projeto Firebase com Firestore habilitado;
- credenciais de serviço do Firebase;
- Git, caso o projeto seja clonado.

### Com Gradle Wrapper

No Windows:

```powershell
.\gradlew.bat bootRun
```

No Linux/macOS:

```bash
./gradlew bootRun
```

A API ficará disponível em `http://localhost:8081`.

### Gerar o JAR

```bash
./gradlew clean build
java -jar build/libs/MVP-0.0.1-SNAPSHOT.jar
```

No Windows, use `gradlew.bat` no lugar de `./gradlew`.

### Docker Compose

O Compose constrói e executa somente o serviço da aplicação:

```bash
docker compose up --build
```

O container expõe a porta `8081`. As credenciais do Firebase não são copiadas pelo `Dockerfile` nem montadas automaticamente pelo `compose.yaml`; configure-as de forma externa antes de usar esse modo.

## Testes

Execute a suíte com:

```bash
./gradlew test
```

A cobertura atual inclui:

- carregamento do contexto Spring;
- conversão de `valorContrato` vindo como texto do Firestore para `BigDecimal` no `BusinessRepository`.

Ainda são recomendados testes para controllers, serviços, `UserRepository`, autenticação, validação HTTP e execução em Docker.

## Próximos passos recomendados

1. Integrar o filtro JWT a uma `SecurityFilterChain` e criar o fluxo de login.
2. Externalizar a chave secreta e as credenciais do Firebase.
3. Adicionar testes de integração dos endpoints.
4. Implementar os módulos de clientes e dashboard, caso façam parte do escopo do MVP.
5. Considerar `LocalDate`/`LocalDateTime` para o campo `data`, em vez de `String`.

## Licença

Nenhuma licença foi definida no repositório até o momento.
