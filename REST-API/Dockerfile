FROM eclipse-temurin:17-jdk-alpine

ENV APP_HOME=/app

WORKDIR $APP_HOME

COPY target/REST-API-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]
