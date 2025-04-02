# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory into the container
COPY Foyer-0.0.1.jar app.jar

# Expose port 8081
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

