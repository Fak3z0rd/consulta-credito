FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/credit.api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
