# Ecommerce Store

## 🚀 Project Overview
A modern ecommerce platform built with Java Spring Boot, following SOLID principles, with PostgreSQL database, ELK stack for logging, and Docker containerization.

## 🏗️ Architecture
- **Backend**: Java Spring Boot 3.x
- **Database**: PostgreSQL 15
- **Logging**: ELK Stack (Elasticsearch, Logstash, Kibana)
- **Containerization**: Docker & Docker Compose
- **API Documentation**: OpenAPI 3.0 (Swagger)
- **Testing**: JUnit 5, Mockito, TestContainers

## 🎯 SOLID Principles Implementation
- **S**ingle Responsibility Principle: Each class has one reason to change
- **O**pen/Closed Principle: Open for extension, closed for modification
- **L**iskov Substitution Principle: Subtypes are substitutable for their base types
- **I**nterface Segregation Principle: Clients depend only on interfaces they use
- **D**ependency Inversion Principle: High-level modules don't depend on low-level modules

## 🗂️ Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/ecommerce/
│   │       ├── config/          # Configuration classes
│   │       ├── controller/      # REST controllers
│   │       ├── service/         # Business logic
│   │       ├── repository/      # Data access layer
│   │       ├── entity/          # JPA entities
│   │       ├── dto/             # Data Transfer Objects
│   │       ├── exception/       # Custom exceptions
│   │       └── util/            # Utility classes
│   └── resources/
│       ├── application.yml      # Application configuration
│       └── db/migration/        # Database migrations
├── test/                        # Test classes
docker/                          # Docker configuration
elk/                             # ELK stack configuration
```

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- PostgreSQL 15

### Quick Start
1. Clone the repository
2. Run `docker-compose up -d` to start PostgreSQL and ELK stack
3. Run `mvn spring-boot:run` to start the application
4. Access the API at `http://localhost:8080`
5. Access Kibana at `http://localhost:5601`

## 📋 Features
- [ ] User authentication & authorization
- [ ] Product catalog management
- [ ] Shopping cart functionality
- [ ] Order management
- [ ] Payment integration
- [ ] Inventory management
- [ ] User reviews & ratings
- [ ] Admin dashboard
- [ ] Analytics & reporting

## 🔧 Development
- **Branch Strategy**: Git Flow
- **Code Quality**: SonarQube integration
- **CI/CD**: GitHub Actions
- **API Testing**: Postman collections

## 📚 API Documentation
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI Spec: `http://localhost:8080/v3/api-docs`

## 🐳 Docker Services
- **Application**: Spring Boot app
- **Database**: PostgreSQL 15
- **Elasticsearch**: Log aggregation
- **Logstash**: Log processing
- **Kibana**: Log visualization

## 📝 License
MIT License