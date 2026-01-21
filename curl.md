# API Investress – Guia de uso com cURL

Este documento apresenta exemplos de requisições **cURL** alinhados **exatamente** com os DTOs atuais da API Investress.

---

## 🔐 Autenticação (`/auth`)

### Registrar usuário

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "pedro@email.com",
    "nome": "Pedro",
    "password": "123456"
  }'
```

### Login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "pedro@email.com",
    "password": "123456"
  }'
```

➡️ Resposta: **JWT** (string)

---

## 💰 Investimentos (`/ws/investimento`)

> Todos os endpoints abaixo exigem o header:

```http
Authorization: Bearer <TOKEN>
```

### Criar investimento

Alinhado com `InvestimentoDTO`

```bash
curl -X POST http://localhost:8080/ws/investimento \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "PETR4",
    "valorInvestido": 1000.00,
    "valorAtual": 1200.00,
    "type": "ACAO"
  }'
```

### Listar investimentos (paginado)

```bash
curl -X GET "http://localhost:8080/ws/investimento?page=0" \
  -H "Authorization: Bearer <TOKEN>"
```

### Buscar investimento por ID

```bash
curl -X GET "http://localhost:8080/ws/investimento?id=1" \
  -H "Authorization: Bearer <TOKEN>"
```

### Buscar investimentos por nome

```bash
curl -X GET "http://localhost:8080/ws/investimento?nome=PETR&page=0" \
  -H "Authorization: Bearer <TOKEN>"
```

### Buscar investimentos agrupados por tipo

```bash
curl -X GET http://localhost:8080/ws/investimento/tipo \
  -H "Authorization: Bearer <TOKEN>"
```

### Buscar investimentos por tipo

```bash
curl -X GET http://localhost:8080/ws/investimento/ACAO \
  -H "Authorization: Bearer <TOKEN>"
```

### Deletar investimento

```bash
curl -X DELETE http://localhost:8080/ws/investimento/1 \
  -H "Authorization: Bearer <TOKEN>"
```

---

## 📉 Stress Test (`/ws/stress-test`)

### Executar teste de estresse

Alinhado com `StressTestContext`

```bash
curl -X POST "http://localhost:8080/ws/stress-test?scenario=CRISE_2008&metrics=DRAWDOWN&metrics=VAR" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "typeInvestimento": "ACAO",
    "shock": {
      "name": "CRASH",
      "impact": -0.30
    },
    "confidence": 0.95
  }'
```

---

## 📌 Observações

* Substitua `<TOKEN>` pelo JWT retornado no login
* Valores monetários utilizam `BigDecimal`
* Enums devem respeitar exatamente os valores definidos na API
* A base URL pode variar conforme o ambiente

---

Projeto: **api-investress**
