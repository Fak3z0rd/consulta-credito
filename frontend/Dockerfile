# Imagem para desenvolvimento local
FROM node:20-alpine

# Define o diretório de trabalho
WORKDIR /app

# Instala dependências do sistema necessárias
RUN apk add --no-cache python3 make g++

# Copia os arquivos de dependências
COPY package*.json ./

# Instala todas as dependências (incluindo devDependencies)
# Força reinstalação para resolver problemas de arquitetura
RUN rm -rf node_modules package-lock.json && npm install

# Copia o código fonte
COPY . .

# Expõe a porta 4200 (Angular dev server)
EXPOSE 4200

# Comando para iniciar o servidor de desenvolvimento
CMD ["npm", "start", "--", "--host", "0.0.0.0", "--port", "4200"] 