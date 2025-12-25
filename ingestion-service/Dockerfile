# Use a modern, supported JDK image
FROM eclipse-temurin:17-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file (already built by IntelliJ)
COPY target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]