# 🥋 Sistema de Gestão de Academia BJJ (Brazilian Jiu-Jitsu)

API RESTful completa para gerenciamento de academias de Jiu-Jitsu, abrangendo o cadastro e controle de **alunos**, **instrutores**, **responsáveis**, **graduações de faixas** e **mensalidades/cobranças** com fluxo financeiro.

O projeto foi construído seguindo os princípios de **Clean Architecture (Arquitetura Limpa)**, desacoplando completamente as regras de negócio de frameworks e persistência externa.

---

## 🏛️ Arquitetura Multi-Módulo

A solução está organizada em três módulos Maven com clara separação de responsabilidades e inversão de dependências:

```
projeto-bjj (Root)
│
├── sistema_gestao_pagamento_core
│   └── Entidades de domínio puras (Person, Student, Instructor, Membership, Graduation, etc.)
│   └── Enums de negócio (Belt, ContractTime, PaymentStatus, Relationship)
│   └── Exceções de domínio invariantes (sem dependência de frameworks)
│
├── sistema_gestao_pagamento_usecases
│   └── Casos de uso da aplicação (SaveStudent, PayMembership, CancelMembership, etc.)
│   └── Interfaces de Repositório (Portas de saída / Inversão de dependência)
│
└── sistema_gestao_pagamento_application
    └── Controladores REST (Spring MVC) e DTOs de Request/Response
    └── Segurança (Spring Security + JWT + Refresh Token)
    └── Persistência (Adapters JPA, Entidades de Banco, Spring Data Repositories)
    └── Configurações de Beans e Injeção de Dependências
    └── Documentação interativa via Swagger / OpenAPI
```

---

## 🚀 Tecnologias & Ferramentas

- **Linguagem**: Java 25 (LTS)
- **Framework Principal**: Spring Boot 4.1.1
- **Segurança**: Spring Security com tokens stateless **JWT (jjwt)** e **Refresh Tokens** persistidos
- **Persistência**: Spring Data JPA & Hibernate ORM
- **Bancos de Dados**:
  - **PostgreSQL 16** (Ambiente de Produção / Docker)
  - **H2 Database** em memória (Ambiente de Desenvolvimento e Testes)
- **Documentação da API**: SpringDoc OpenAPI 3 / Swagger UI
- **Testes Automatizados**: JUnit 5, Mockito e Spring MockMvc

---

## ⚡ Principais Funcionalidades

### 1. Autenticação & Segurança
- Registro público inicial de instrutores (`/api/v1/auth/register`).
- Login seguro com geração de Access Token (curta duração) e Refresh Token (30 dias).
- Endpoint para renovação automática de token (`/api/v1/auth/refresh`).
- Proteção de rotas via `Bearer JWT` nos headers das requisições.

### 2. Gestão de Alunos & Responsáveis
- Cadastro completo de alunos com dados pessoais e histórico médico (peso, altura, problemas de saúde).
- Vínculo opcional a um responsável financeiro/legal para menores de idade.
- Atualização cadastral e médica (`PUT /api/v1/bjj/students/{id}`).
- Ativação e inativação de matrículas (`PATCH /activate` e `PATCH /inactivate`).
- Listagem geral e filtro por alunos ativos (`GET /api/v1/bjj/students?active=true`).

### 3. Graduações & Faixas
- Cadastro de níveis de faixa e graus (`WHITE`, `BLUE`, `PURPLE`, `BROWN`, `BLACK`).
- Registro do histórico de graduação de cada aluno associando a data e o instrutor responsável.

### 4. Controle Financeiro & Mensalidades
- Emissão de mensalidades com prazos contratuais flexíveis (`MONTHLY`, `QUARTERLY`, `SEMIANNUAL`, `ANNUAL`).
- Detecção automática de vencimentos e inadimplência (`isOverdue`).
- **Baixa de Pagamento**: Endpoint `PATCH /api/v1/bjj/memberships/{id}/pay` atualiza o status para `PAID` e registra a data do pagamento.
- **Cancelamento**: Endpoint `PATCH /api/v1/bjj/memberships/{id}/cancel` para cancelamento de cobranças.
- Consulta por aluno ou por status (`PENDING`, `PAID`, `CANCELLED`).

---

## ⚙️ Configuração & Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto (ou configure as variáveis no seu ambiente) para execução com PostgreSQL e JWT:

```properties
# Configuração do Banco de Dados PostgreSQL (Docker / Prod)
BD_HOST=localhost
BD_PORT=5432
BD_NAME=bjj_db
BD_USERNAME=postgres
BD_PASSWORD=postgres

# Configuração de Segurança JWT
JWT_SECRET=sua_chave_secreta_super_segura_de_no_minimo_256_bits_aqui
JWT_EXPIRATION=86400000 # 24 horas em milissegundos
```

---

## 🏃 Como Executar a Aplicação

### 1. Subir o Banco PostgreSQL (via Docker Compose)
```bash
docker compose up -d
```

### 2. Executar a Aplicação

#### Modo Desenvolvimento (Perfil `dev` com banco H2 em memória):
```bash
./sistema_gestao_pagamento_application/mvnw spring-boot:run \
  -pl sistema_gestao_pagamento_application \
  -Dspring-boot.run.profiles=dev
```

#### Modo Produção (Perfil `prod` conectado ao PostgreSQL):
```bash
./sistema_gestao_pagamento_application/mvnw spring-boot:run \
  -pl sistema_gestao_pagamento_application \
  -Dspring-boot.run.profiles=prod
```

---

## 📖 Documentação da API (Swagger & H2 Console)

Com a aplicação em execução:

- **Swagger UI (Documentação Interativa)**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- **Console H2** (no perfil `dev`): [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - JDBC URL: `jdbc:h2:mem:bjjdb`
  - User: `sa`
  - Senha: *(em branco)*

---

## 🧪 Como Executar os Testes

Para executar toda a suíte de testes unitários e testes integrados:

```bash
./sistema_gestao_pagamento_application/mvnw test
```

Para rodar os testes de um módulo específico:

```bash
# Testes do Core
./sistema_gestao_pagamento_application/mvnw test -pl sistema_gestao_pagamento_core

# Testes de Casos de Uso
./sistema_gestao_pagamento_application/mvnw test -pl sistema_gestao_pagamento_usecases -am

# Testes da Aplicação (Integrados)
./sistema_gestao_pagamento_application/mvnw test -pl sistema_gestao_pagamento_application -am
```

---

## 🛣️ Resumo dos Principais Endpoints

| Método | Endpoint | Descrição | Autenticação |
| :--- | :--- | :--- | :---: |
| `POST` | `/api/v1/auth/register` | Cadastro inicial de instrutor | Pública |
| `POST` | `/api/v1/auth/login` | Login (gera Access e Refresh Token) | Pública |
| `POST` | `/api/v1/auth/refresh` | Renova Access Token via Refresh Token | Pública |
| `GET` | `/api/v1/bjj/students` | Listar alunos (suporta `?active=true`) | Bearer JWT |
| `POST` | `/api/v1/bjj/students` | Cadastrar novo aluno | Bearer JWT |
| `GET` | `/api/v1/bjj/students/{id}` | Buscar aluno por ID | Bearer JWT |
| `PUT` | `/api/v1/bjj/students/{id}` | Atualizar dados físicos e médicos | Bearer JWT |
| `PATCH` | `/api/v1/bjj/students/{id}/activate` | Ativar matrícula do aluno | Bearer JWT |
| `PATCH` | `/api/v1/bjj/students/{id}/inactivate` | Inativar matrícula do aluno | Bearer JWT |
| `POST` | `/api/v1/bjj/responsibles` | Cadastrar responsável | Bearer JWT |
| `GET` | `/api/v1/bjj/responsibles` | Listar responsáveis | Bearer JWT |
| `POST` | `/api/v1/bjj/graduations` | Cadastrar graduação/faixa | Bearer JWT |
| `POST` | `/api/v1/bjj/student-graduations` | Graduar um aluno (histórico de faixa) | Bearer JWT |
| `POST` | `/api/v1/bjj/memberships` | Gerar mensalidade / cobrança | Bearer JWT |
| `GET` | `/api/v1/bjj/memberships` | Listar todas as mensalidades | Bearer JWT |
| `GET` | `/api/v1/bjj/memberships/{id}` | Buscar mensalidade por ID | Bearer JWT |
| `GET` | `/api/v1/bjj/memberships/student/{id}` | Mensalidades por aluno | Bearer JWT |
| `GET` | `/api/v1/bjj/memberships/status/{status}` | Mensalidades por status | Bearer JWT |
| `PATCH` | `/api/v1/bjj/memberships/{id}/pay` | Pagar/Dar baixa em mensalidade | Bearer JWT |
| `PATCH` | `/api/v1/bjj/memberships/{id}/cancel` | Cancelar mensalidade | Bearer JWT |

