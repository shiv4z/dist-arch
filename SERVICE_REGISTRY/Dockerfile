FROM eclipse-temurin:17-jdk-alpine

ENV APP_HOME=/app

WORKDIR $APP_HOME

COPY target/SERVICE_REGISTRY-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]
