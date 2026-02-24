# Production Optimizer

Sistema de otimização de planejamento de produção baseado em algoritmo guloso (greedy algorithm), com gestão completa de produtos e matérias-primas.

## 📌 Sobre o Projeto

O Production Optimizer foi desenvolvido com o objetivo de simular um cenário real de planejamento industrial, onde é necessário decidir o que produzir e em qual quantidade, considerando limitações de estoque e priorização por valor de venda.

A aplicação permite:

- Gerenciar matérias-primas disponíveis
- Cadastrar produtos com suas respectivas composições
- Executar um cálculo automático que determina a melhor estratégia de produção com base nos recursos disponíveis

O foco do projeto está na clareza da arquitetura, separação de responsabilidades e implementação objetiva da regra de negócio.

## 🚀 Funcionalidades

### ✔ Gestão de Matérias-Primas
- Cadastro, edição e exclusão
- Controle de estoque
- Listagem completa via API REST

### ✔ Gestão de Produtos
- Cadastro de produtos com valor de venda
- Associação de matérias-primas e quantidades necessárias
- Edição e exclusão

### ✔ Otimização de Produção
Implementação de algoritmo guloso que:
- Ordena produtos pelo maior valor de venda
- Calcula a quantidade máxima possível de produção com base no estoque
- Atualiza o saldo das matérias-primas após cada decisão
- Maximiza o retorno dentro das restrições disponíveis

## 🛠 Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 3.2
- Spring Data JPA
- Banco H2 (em memória)
- Maven

### Frontend
- Vue.js 3
- Vite
- Vue Router
- Vue i18n
- Axios

## 📂 Estrutura do Projeto

```
production-optimizer/
├── backend/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   ├── dto/
│   └── exception/
│
├── frontend/
│   ├── views/
│   ├── router/
│   ├── api/
│   ├── i18n/
│   └── dist/
│
└── README.md
```

A organização segue uma arquitetura em camadas no backend (Controller → Service → Repository), garantindo separação clara de responsabilidades.

## ▶ Como Executar Localmente

### Pré-requisitos
- Java 17+
- Node.js (LTS)
- Maven

### 🔹 Backend
```
bash
cd backend
./mvnw spring-boot:run
```

API disponível em: **http://localhost:8080**

Executar testes:
```
bash
./mvnw test
```

### 🔹 Frontend
```
bash
cd frontend
npm install
npm run dev
```

Aplicação disponível em: **http://localhost:3000**

Gerar build de produção:
```
bash
npm run build
```

## 🔗 Endpoints da API

### Matérias-Primas

| Método | Endpoint |
|--------|----------|
| GET | /api/raw-materials |
| GET | /api/raw-materials/{id} |
| POST | /api/raw-materials |
| PUT | /api/raw-materials/{id} |
| DELETE | /api/raw-materials/{id} |

### Produtos

| Método | Endpoint |
|--------|----------|
| GET | /api/products |
| GET | /api/products/{id} |
| POST | /api/products |
| PUT | /api/products/{id} |
| DELETE | /api/products/{id} |

### Otimização

| Método | Endpoint |
|--------|----------|
| POST | /api/optimization/optimize |

## 🧠 Estratégia de Otimização

O algoritmo implementado segue a abordagem gulosa:

1. Produtos são ordenados por maior valor de venda
2. Para cada produto, calcula-se o limite máximo de produção com base na matéria-prima disponível
3. Produz-se o máximo possível
4. Atualiza-se o estoque restante antes de avaliar o próximo item

Essa abordagem privilegia retorno financeiro imediato, mantendo a lógica simples, eficiente e previsível.

## 🧪 Ambiente de Desenvolvimento

Console H2 disponível em: **http://localhost:8080/h2-console**

JDBC URL: `jdbc:h2:mem:productiondb`

## ⏱ Tempo de Desenvolvimento

Este projeto foi desenvolvido em aproximadamente **11h43m**, no dia **24/02**, com foco em:

- Estrutura organizada
- Clareza na regra de negócio
- Separação adequada entre backend e frontend
- Implementação funcional do algoritmo

## 📄 Observações

Este projeto tem caráter educacional e demonstrativo, com foco na aplicação prática de conceitos de:

- Arquitetura em camadas
- API REST
- Integração frontend/backend
- Algoritmos de otimização
- Organização de código para avaliação técnica
