#!/bin/bash

echo "Construindo aplicação..."

JAR_NAME="credit.api-0.0.1-SNAPSHOT.jar"
JAR_PATH="build/libs/$JAR_NAME"
IMAGE_NAME="credit-api"

if [[ ! -f "$JAR_PATH" ]]; then
    echo "Erro: Arquivo JAR não encontrado em $JAR_PATH. Gerando build com gradlew"
    ./gradlew clean build -x test
else 
    echo "Arquivo JAR encontrado em $JAR_PATH"
fi

NEW_JAR_HASH=$(sha256sum $JAR_PATH | awk '{print $1}')
HASH_FILE="jar_hash.txt"

if [[ -f "$HASH_FILE" && "$(cat $HASH_FILE)" != "$NEW_JAR_HASH" ]]; then
    echo "Arquivo JAR foi alterado. Reconstruindo imagem Docker..."
    docker build -t $IMAGE_NAME .
else
    echo "Arquivo JAR não foi alterado. Utilizando imagem existente."
fi

echo "Subindo container..."
docker-compose up --build
