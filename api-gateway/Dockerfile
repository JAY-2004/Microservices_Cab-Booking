# Use OpenJDK base image
FROM openjdk:23-jdk-slim
# Set working directory
WORKDIR /app
# Copy JAR file to container
COPY target/api-gateway-0.0.1-SNAPSHOT.ja /app/demo.jar
# Expose port 8080
EXPOSE 9090
# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]