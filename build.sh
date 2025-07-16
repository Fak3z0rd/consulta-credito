#!/bin/bash

echo "Iniciando aplicação..."

# Configurações
JAR_NAME="credit.api-0.0.1-SNAPSHOT.jar"
JAR_PATH="build/libs/$JAR_NAME"
BACKEND_IMAGE_NAME="credit-api-backend"
FRONTEND_IMAGE_NAME="credit-api-frontend"

# 1. Build do Backend (Spring Boot)
echo "Executando build do backend com Gradle..."
./gradlew clean build -x test

if [[ ! -f "$JAR_PATH" ]]; then
    echo "Erro: Arquivo JAR não foi gerado em $JAR_PATH"
    exit 1
fi

echo "Backend JAR gerado com sucesso em $JAR_PATH"

# 2. Verificar mudanças para otimizar rebuild das imagens Docker
NEW_JAR_HASH=$(sha256sum $JAR_PATH | awk '{print $1}')
HASH_FILE="jar_hash.txt"

# Backend
if [[ -f "$HASH_FILE" && "$(cat $HASH_FILE)" == "$NEW_JAR_HASH" ]]; then
    echo "Backend JAR não foi alterado. Utilizando imagem Docker existente."
else
    echo "Backend JAR foi alterado. Reconstruindo imagem Docker..."
    docker build -t $BACKEND_IMAGE_NAME .
    echo "$NEW_JAR_HASH" > $HASH_FILE
fi

# Frontend
echo "Reconstruindo imagem Docker do frontend..."
docker build -t $FRONTEND_IMAGE_NAME ./frontend

# 3. Subir todos os serviços
echo "Subindo containers com Docker Compose..."
docker-compose up --build
