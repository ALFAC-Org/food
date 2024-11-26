# Use a imagem com JDK e Maven para compilar a aplicacao
FROM maven:3.8.1-openjdk-17-slim AS build

# Define o WORKDIR no container
WORKDIR /app

# Copia o arquivo pom.xml para o WORKDIR
COPY pom.xml .

# Copia os modulos para o WORKDIR
COPY food-main ./food-main
COPY food-infra ./food-infra
COPY food-core ./food-core

# Compila o aplicativo com o Maven
RUN mvn clean install -U -DskipTests

# Crie uma imagem baseada na JDK para executar a aplicacao
FROM openjdk:17-slim

# Define o WORKDIR no container
WORKDIR /app

# Copia o JAR da aplicacao para o WORKDIR
COPY --from=build /app/food-main/target/*.jar ./app.jar

# Executa a aplicacao
CMD ["java", "-Dspring.profiles.active=prod", "-Dlogging.level.root=DEBUG", "-jar", "app.jar"]
