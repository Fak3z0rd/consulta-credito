# Sistema de Consulta de Créditos

Sistema full-stack para consulta de créditos por NFS-e ou número do crédito, desenvolvido com Spring Boot (backend) e Angular (frontend).

## Pré-requisitos

- **Node.js 20+** (para desenvolvimento local do frontend)
- **Java 21+** (para desenvolvimento local do backend)
- **Docker e Docker Compose** (para ambiente containerizado)
- **Git**

## Estrutura do Projeto

```
credit.api/
├── src/                    # Backend Spring Boot
│   ├── main/java/
│   └── main/resources/
├── frontend/               # Frontend Angular
│   ├── src/app/
│   ├── package.json
│   └── Dockerfile
├── docker-compose.yml      # Orquestração dos containers
├── build.sh               # Script de build automatizado
└── README.md
```

## Opções de Execução

### Opção 1: Ambiente Completo com Docker (Recomendado)

Execute o script automatizado que configura todo o ambiente:

```bash
./build.sh
```

Este comando irá:
- Fazer build do backend Spring Boot
- Instalar dependências do frontend
- Construir imagens Docker
- Subir todos os serviços

### Opção 2: Execução Manual com Docker Compose

```bash
# Build do backend
./gradlew clean build -x test

# Instalar dependências do frontend
cd frontend
npm install
cd ..

# Subir containers
docker-compose up --build
```

### Opção 3: Desenvolvimento Local (Sem Docker)

#### Backend (Spring Boot)

```bash
# Executar aplicação
./gradlew bootRun

# Ou buildar e executar JAR
./gradlew clean build -x test
java -jar build/libs/credit.api-0.0.1-SNAPSHOT.jar
```

#### Frontend (Angular)

```bash
cd frontend

# Instalar dependências
npm install

# Executar servidor de desenvolvimento
npm start
```

## Acesso aos Serviços

Após a execução, os serviços estarão disponíveis em:

- **Frontend**: http://localhost
- **Backend API**: http://localhost:8080
- **PostgreSQL**: localhost:5432
- **Kafka**: localhost:9092

## Funcionalidades

### Consulta de Créditos

O sistema permite consultar créditos de duas formas:

1. **Por Número da NFS-e**: Busca todos os créditos relacionados a uma NFS-e específica
2. **Por Número do Crédito**: Busca um crédito específico pelo seu número

### Interface

- Design responsivo com Bootstrap 5
- Interface moderna e intuitiva
- Feedback visual para o usuário
- Tratamento de erros amigável

## Tecnologias Utilizadas

### Backend
- **Spring Boot 3.x**: Framework Java
- **Spring Data JPA**: Persistência de dados
- **PostgreSQL**: Banco de dados
- **Apache Kafka**: Mensageria
- **Gradle**: Build tool

### Frontend
- **Angular 19**: Framework JavaScript
- **Bootstrap 5**: Framework CSS
- **Bootstrap Icons**: Ícones
- **SweetAlert2**: Alertas
- **RxJS**: Programação reativa

## Desenvolvimento

### Hot Reload

O ambiente de desenvolvimento suporta hot reload:

- **Frontend**: Mudanças no código Angular são refletidas automaticamente
- **Backend**: Spring Boot DevTools reinicia automaticamente

### Estrutura de Volumes (Docker)

```yaml
volumes:
  - ./frontend/src:/app/src          # Hot reload do código
  - ./frontend/node_modules:/app/node_modules  # Cache das dependências
```

### Comandos Úteis

```bash
# Ver logs dos containers
docker-compose logs -f

# Parar todos os serviços
docker-compose down

# Rebuildar apenas o backend
docker-compose up --build backend

# Rebuildar apenas o frontend
docker-compose up --build frontend

# Acessar shell do container backend
docker-compose exec backend bash

# Acessar shell do container frontend
docker-compose exec frontend sh
```

## Configuração do Banco de Dados

O PostgreSQL é configurado automaticamente com:

- **Database**: creditdb
- **Usuário**: postgres
- **Senha**: password
- **Porta**: 5432

Os dados são persistidos em volume Docker.

## API Endpoints

### Consulta por NFS-e
```
GET /api/creditos/{nfse}
```

### Consulta por Número do Crédito
```
GET /api/creditos/numero/{numeroCredito}
```

## Troubleshooting

### Problemas Comuns

1. **Porta já em uso**
   ```bash
   # Verificar portas em uso
   lsof -i :8080
   lsof -i :4200
   ```

2. **Erro de permissão no build.sh**
   ```bash
   chmod +x build.sh
   ```

3. **Node.js versão incorreta**
   ```bash
   # Usar nvm para gerenciar versões
   nvm use 20
   ```

4. **Docker sem recursos suficientes**
   - Aumentar memória e CPU no Docker Desktop

### Logs de Debug

```bash
# Logs do backend
docker-compose logs backend

# Logs do frontend
docker-compose logs frontend

# Logs do banco
docker-compose logs postgres
```
