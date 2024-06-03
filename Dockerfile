# Build stage
FROM maven:3.8.3-openjdk-17 AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el pom.xml y el código fuente
COPY pom.xml .
COPY src ./src

# Ejecutar la construcción de Maven
RUN mvn clean package -Pprod -DskipTests

# Package stage
FROM eclipse-temurin:17-jdk

COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar demo.jar

EXPOSE 8086

ENTRYPOINT ["java","-jar","demo.jar"]
