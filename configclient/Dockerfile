FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy build output
COPY build/libs/configclient-0.0.1-SNAPSHOT.jar clientapp.jar

# Expose new port (e.g., 8082)
EXPOSE 8082

# Run the app on port 8082
ENTRYPOINT ["java", "-jar", "clientapp.jar", "--server.port=8082"]
