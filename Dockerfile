# Use an appropriate base image with Java
FROM openjdk:17-jdk-slim AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first (for caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (this speeds up subsequent builds)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy the rest of the application code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Second stage: create the runtime image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# (Optional) If you want to run on a specific port, for example 8082
EXPOSE 8082

# Environment variables (modify as needed)
ENV DB_HOST=127.0.0.1
ENV DB_NAME=inventory_db
ENV DB_PASS=postgres
ENV DB_PORT=5432
ENV DB_USERNAME=postgres
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseParallelGC -XX:MinHeapFreeRatio=10 -XX:MaxHeapFreeRatio=20 -XX:GCTimeRatio=4 -XX:AdaptiveSizePolicyWeight=90 -XX:MaxMetaspaceSize=100m -XX:ParallelGCThreads=1 -XX:CICompilerCount=2 -XX:+ExitOnOutOfMemoryError"

# Command to run the jar
ENTRYPOINT [ "sh", "-c", "java ${JAVA_OPTS} -jar /app/app.jar" ]
