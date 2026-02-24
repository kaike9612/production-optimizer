# Production Optimizer

Sistema completo de otimização de planejamento de produção utilizando algoritmo guloso (greedy algorithm).

## Descrição do Projeto

O **Production Optimizer** é uma aplicação full-stack desenvolvida para otimizar o processo de planejamento de produção industrial. O sistema permite a gestão eficiente de produtos e matérias-primas, calculando automaticamente a melhor combinação de recursos disponíveis para maximizar a produção com base nas restrições de insumos.

### Principais Funcionalidades

- **Gestão de Matérias-Primas**: Cadastro, edição, visualização e exclusão de matérias-primas disponíveis em estoque
- **Gestão de Produtos**: Cadastro de produtos com composição definida por matérias-primas
- **Otimização de Produção**: Algoritmo guloso que calcula a quantidade ideal de cada produto a ser fabricado, considerando:
  - Valor de venda dos produtos (prioridade para produtos de maior valor)
  - Disponibilidade de matérias-primas em estoque
  - Composições definidas para cada produto

## Tecnologias Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Banco de Dados H2** (em memória)
- **Maven**

### Frontend
- **Vue.js 3**
- **Vite** (build tool)
- **Vue Router** (gerenciamento de rotas)
- **Vue i18n** (internacionalização)
- **Axios** (comunicação com API)

## Estrutura de Pastas

```
production-optimizer/
├── backend/                    # API REST Spring Boot
│   ├── src/main/java/         # Código fonte Java
│   │   └── com/example/productionoptimizer/
│   │       ├── controller/    # Controladores REST
│   │       ├── service/       # Lógica de negócio
│   │       ├── repository/    # Repositórios JPA
│   │       ├── entity/        # Entidades do banco
│   │       ├── dto/           # Data Transfer Objects
│   │       └── exception/     # Tratamento de exceções
│   ├── src/main/resources/    # Arquivos de configuração
│   ├── src/test/              # Testes unitários
│   └── pom.xml                # Dependências Maven
│
├── frontend/                  # Aplicação Vue.js
│   ├── src/
│   │   ├── views/             # Componentes de página
│   │   ├── router/            # Configuração de rotas
│   │   ├── api/               # Configuração Axios
│   │   ├── i18n/              # Mensagens internacionalizadas
│   │   ├── App.vue            # Componente principal
│   │   └── main.js            # Entry point
│   ├── dist/                  # Build de produção
│   ├── package.json           # Dependências npm
│   └── vite.config.js         # Configuração Vite
│
└── README.md                  # Este arquivo
```

## Instruções para Executar Localmente

### Pré-requisitos

- Java 17 ou superior
- Node.js (versão LTS)
- Maven

### Backend

1. Acesse o diretório do backend:

```
bash
cd backend
```

2. Compile e execute a aplicação:

```
bash
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

3. Para executar os testes:

```
bash
./mvnw test
```

### Frontend

1. Acesse o diretório do frontend:

```
bash
cd frontend
```

2. Instale as dependências:

```
bash
npm install
```

3. Execute o servidor de desenvolvimento:

```
bash
npm run dev
```

A aplicação frontend estará disponível em: `http://localhost:3000`

4. Para gerar o build de produção:

```
bash
npm run build
```

## Endpoints da API

### Matérias-Primas

| Método | Endpoint | Descrição |
|--------|----------|------------|
| GET | /api/raw-materials | Listar todas as matérias-primas |
| GET | /api/raw-materials/{id} | Obter matéria-prima por ID |
| POST | /api/raw-materials | Criar nova matéria-prima |
| PUT | /api/raw-materials/{id} | Atualizar matéria-prima |
| DELETE | /api/raw-materials/{id} | Excluir matéria-prima |

### Produtos

| Método | Endpoint | Descrição |
|--------|----------|------------|
| GET | /api/products | Listar todos os produtos |
| GET | /api/products/{id} | Obter produto por ID |
| POST | /api/products | Criar novo produto |
| PUT | /api/products/{id} | Atualizar produto |
| DELETE | /api/products/{id} | Excluir produto |

### Otimização

| Método | Endpoint | Descrição |
|--------|----------|------------|
| POST | /api/optimization/optimize | Executar algoritmo de otimização |

## Algoritmo de Otimização

O sistema utiliza um algoritmo guloso para otimizar a produção:

1. **Ordenação**: Produtos são ordenados por valor de venda (maior primeiro)
2. **Cálculo**: Para cada produto, calcula-se a quantidade máxima producible com base nas matérias-primas disponíveis
3. **Produção**: Fabrica-se a maior quantidade possível respeitando as restrições de materiais
4. **Atualização**: Após cada produto, atualiza-se o restante das matérias-primas disponíveis

## Console de Desenvolvimento

Para acessar o console H2 (apenas desenvolvimento):
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:productiondb`

## Informações do Desenvolvimento

Este projeto foi desenvolvido em aproximadamente **11h43m** no dia **24/02**.

## Repositório

O código fonte está disponível em: [https://github.com/kaike9612/production-optimizer](https://github.com/kaike9612/production-optimizer)

## Licença

Este projeto é para fins educacionais e de demonstração.
