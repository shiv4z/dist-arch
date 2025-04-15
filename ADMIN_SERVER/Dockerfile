FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

ENV APP_HOME=/app
WORKDIR $APP_HOME

COPY --from=build /build/target/ADMIN_SERVER-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 1111

ENTRYPOINT ["java", "-jar", "app.jar"]
