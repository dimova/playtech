# Dockerfile
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY demo/build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
