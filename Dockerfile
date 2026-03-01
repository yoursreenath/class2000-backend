# ── Stage 1: Build the JAR using Maven ──────────────────────────────
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml first (caches dependencies layer if pom doesn't change)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# ── Stage 2: Run the JAR on a slim JRE image ────────────────────────
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built JAR from Stage 1
COPY --from=build /app/target/reunion-1.0.0.jar app.jar

# Render injects PORT env var — Spring reads it via ${PORT:8080}
EXPOSE 8080

# Activate the prod profile so application-prod.properties is used
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
