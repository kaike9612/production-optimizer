# Production Optimizer

A full-stack application for optimizing production planning using a greedy algorithm.

## Project Structure

```
production-optimizer/
├── backend/           # Spring Boot API
├── frontend/          # Vue.js 3 SPA
└── README.md
```

## Prerequisites

- Java 17
- Node.js (LTS version)
- Maven

## Backend Setup

1. Navigate to the backend directory:
   
```
bash
   cd backend
   
```

2. Build and run the application:
   
```
bash
   ./mvnw spring-boot:run
   
```

   The API will be available at `http://localhost:8080`

3. To run tests:
   
```
bash
   ./mvnw test
   
```

## Frontend Setup

1. Navigate to the frontend directory:
   
```
bash
   cd frontend
   
```

2. Install dependencies:
   
```
bash
   npm install
   
```

3. Run the development server:
   
```
bash
   npm run dev
   
```

   The frontend will be available at `http://localhost:3000`

## API Endpoints

### Raw Materials

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/raw-materials | Get all raw materials |
| GET | /api/raw-materials/{id} | Get raw material by ID |
| POST | /api/raw-materials | Create new raw material |
| PUT | /api/raw-materials/{id} | Update raw material |
| DELETE | /api/raw-materials/{id} | Delete raw material |

### Products

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/products | Get all products |
| GET | /api/products/{id} | Get product by ID |
| POST | /api/products | Create new product |
| PUT | /api/products/{id} | Update product |
| DELETE | /api/products/{id} | Delete product |

### Optimization

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/optimization/optimize | Run optimization algorithm |

## Optimization Algorithm

The application uses a greedy algorithm to optimize production:

1. Sort products by sales value (highest first)
2. For each product, calculate maximum producible quantity based on available raw materials
3. Produce as many units as possible while respecting raw material constraints
4. Update remaining raw materials after each product

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.2.0
- H2 Database
- Spring Data JPA

### Frontend
- Vue.js 3
- Vite
- Axios
- Vue Router
- Vue i18n

## Development

The backend runs on port 8080 and the frontend on port 3000. The frontend is configured to proxy API requests to the backend.

To access the H2 console (for development), go to:
`http://localhost:8080/h2-console`

JDBC URL: `jdbc:h2:mem:productiondb`
