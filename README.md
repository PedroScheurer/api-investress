# 📊 Investress API

**Investress** é uma API monolítica desenvolvida para **gestão de investimentos** e **execução de testes de estresse financeiro**, permitindo análises de risco por meio de métricas consolidadas e cenários configuráveis.  
O projeto foi construído com foco em **boas práticas**, **arquitetura limpa** e **extensibilidade**, utilizando o **Strategy Pattern** para os cálculos financeiros.

---

## 🚀 Visão Geral

A API oferece:

- Cadastro e autenticação de usuários
- Gerenciamento de investimentos
- Consulta de dados históricos de ativos financeiros
- Execução de **Stress Tests**
- Análise de risco baseada em dados históricos e cenários simulados

Os dados de mercado são obtidos através da **API da BRAPI DEV**.

---

## 🏗️ Arquitetura

- **Tipo:** Monolítico
- **Estilo:** API REST
- **Padrões de Projeto:**
  - Strategy Pattern
  - Arquitetura em camadas (Controller, Service, Repository)
  - Segurança baseada em JWT

---

## 🧰 Tecnologias Utilizadas

<table>
  <thead>
    <tr>
      <th>Categoria</th>
      <th>Tecnologia</th>
      <th>Versão / Descrição</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Backend</td>
      <td>Java</td>
      <td>21</td>
    </tr>
    <tr>
      <td>Framework</td>
      <td>Spring Boot</td>
      <td>API REST, Segurança, Validação</td>
    </tr>
    <tr>
      <td>Build</td>
      <td>Maven</td>
      <td>Gerenciamento de dependências</td>
    </tr>
    <tr>
      <td>Banco de Dados</td>
      <td>PostgreSQL</td>
      <td>16</td>
    </tr>
    <tr>
      <td>Persistência</td>
      <td>Spring Data JPA / Hibernate</td>
      <td>ORM</td>
    </tr>
    <tr>
      <td>Segurança</td>
      <td>Spring Security</td>
      <td>Autenticação e autorização</td>
    </tr>
    <tr>
      <td>Autenticação</td>
      <td>JWT</td>
      <td>jjwt 0.12.6</td>
    </tr>
    <tr>
      <td>Observabilidade</td>
      <td>Spring Boot Actuator</td>
      <td>Health checks e métricas</td>
    </tr>
    <tr>
      <td>Integração Externa</td>
      <td>BRAPI DEV</td>
      <td>Dados históricos de ativos</td>
    </tr>
    <tr>
      <td>Testes</td>
      <td>Spring Boot Test</td>
      <td>Web MVC e Security</td>
    </tr>
  </tbody>
</table>

---

## 🔐 Funcionalidades

### 👤 Usuários
- Cadastro de usuários
- Autenticação via JWT
- Controle de acesso aos endpoints protegidos

### 💰 Investimentos
- Cadastro e gerenciamento de investimentos
- Associação de investimentos a usuários
- Tipos de ativos definidos por `ENUM`

**Exemplos de tipos de ativos:**
- `ACAO`
- `FUNDO_IMOBILIARIO`
- `CDB`
- `TESOURO_DIRETO`

---

## 📈 Integração com BRAPI DEV

A API consome a **BRAPI DEV** para obter:

- Histórico de preços de ativos
- Informações utilizadas nos cálculos estatísticos
- Base de dados para os testes de estresse

---

## ⚠️ Stress Test Service

O **StressTestService** é responsável pela execução dos testes de estresse e utiliza o **Strategy Pattern**, permitindo combinações dinâmicas de métricas e cenários.

### 📊 Métricas de Risco
- **Drawdown**
- **Value at Risk (VaR)**

### 🌎 Cenários
- **Historical** – baseado em dados históricos reais
- **Shock** – simulação de cenários extremos de mercado

As métricas e cenários são informados por **parâmetros na requisição**, enquanto o tipo de ativo é enviado no **body**.

---

## 🔄 Fluxo do Stress Test

1. O cliente informa:
   - Tipo de ativo (`ENUM`)
   - Métrica desejada
   - Cenário
2. A API consulta os dados históricos na BRAPI
3. O `StressTestService` seleciona dinamicamente:
   - Estratégia de métrica
   - Estratégia de cenário
4. O resultado do teste de estresse é retornado ao cliente

---

## 🧪 Testes

- Testes de segurança com `spring-boot-starter-security-test`
- Testes de controllers com `spring-boot-starter-webmvc-test`

---

## 🌐 Endpoints da API

Todos os endpoints da aplicação possuem como **path base**: /api



### 🔐 Autenticação (`/api/auth`)

Os endpoints de autenticação **não exigem JWT**.

<table>
  <thead>
    <tr>
      <th>Método HTTP</th>
      <th>Endpoint</th>
      <th>Autenticação</th>
      <th>Descrição</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td>/api/auth/register</td>
      <td>❌ Não</td>
      <td>Cadastro de novos usuários</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>/api/auth/login</td>
      <td>❌ Não</td>
      <td>Autenticação do usuário e geração do token JWT</td>
    </tr>
  </tbody>
</table>

---

### 💰 Investimentos (`/api/ws/investimento`)

⚠️ **Todos os endpoints que possuem `/ws` exigem autenticação via JWT.**

<table>
  <thead>
    <tr>
      <th>Método HTTP</th>
      <th>Endpoint</th>
      <th>Parâmetros</th>
      <th>JWT</th>
      <th>Descrição</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td>/api/ws/investimento</td>
      <td>Body</td>
      <td>✅ Sim</td>
      <td>Cadastra um novo investimento</td>
    </tr>
    <tr>
      <td>GET</td>
      <td>/api/ws/investimento</td>
      <td>page</td>
      <td>✅ Sim</td>
      <td>Lista investimentos de forma paginada</td>
    </tr>
    <tr>
      <td>GET</td>
      <td>/api/ws/investimento</td>
      <td>id</td>
      <td>✅ Sim</td>
      <td>Busca um investimento pelo identificador</td>
    </tr>
    <tr>
      <td>GET</td>
      <td>/api/ws/investimento</td>
      <td>nome, page</td>
      <td>✅ Sim</td>
      <td>Busca investimentos pelo nome com paginação</td>
    </tr>
    <tr>
      <td>GET</td>
      <td>/api/ws/investimento/tipo</td>
      <td>-</td>
      <td>✅ Sim</td>
      <td>Lista todos os investimentos agrupados por tipo</td>
    </tr>
    <tr>
      <td>GET</td>
      <td>/api/ws/investimento/{tipo}</td>
      <td>Path Variable: tipo</td>
      <td>✅ Sim</td>
      <td>Lista investimentos filtrando pelo tipo de ativo</td>
    </tr>
    <tr>
      <td>DELETE</td>
      <td>/api/ws/investimento/{id}</td>
      <td>Path Variable: id</td>
      <td>✅ Sim</td>
      <td>Remove um investimento pelo identificador</td>
    </tr>
  </tbody>
</table>

---

## 🔑 Autenticação JWT

Para acessar qualquer endpoint que contenha `/ws`, é obrigatório enviar o token JWT no header da requisição:

```http
Authorization: Bearer <token>
```
---

## ⚙️ Variáveis de Ambiente

O projeto utiliza variáveis de ambiente para configuração de banco de dados, autenticação e integração externa.  
Essas variáveis são carregadas automaticamente pelo **Docker Compose** a partir do arquivo `.env`.

---

### 📄 Arquivo `.env`

Crie um arquivo `.env` na raiz do projeto com o seguinte conteúdo:

```env
# Banco de Dados
DB_NAME=investress
DB_USERNAME=postgres
DB_PASSWORD=postgres

# Segurança
JWT_SECRET=secret-key

# Integração Externa
BRAPI_TOKEN=seu-token-aqui
```

## ▶️ Executando o Projeto com Docker Compose (Recomendado)

O **Docker Compose** sobe toda a infraestrutura necessária para execução do projeto:

- PostgreSQL 16
- API Investress
- Volume Docker para persistência de dados
- Healthcheck para garantir a ordem correta de inicialização dos serviços

---

### 🧱 Passos

1. Certifique-se de ter instalado:
   - Docker
   - Docker Compose

2. Crie o arquivo `.env` conforme descrito na seção de variáveis de ambiente

3. Execute o comando abaixo para subir o ambiente:

```bash
docker compose up --build
