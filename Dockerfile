# =====================================================
# Multi-stage Dockerfile for Air Ticket Booking System
# =====================================================

# Stage 1: Build the application using Maven
FROM maven:3.9.5-eclipse-temurin-17 AS builder

LABEL maintainer="DevOps Team <devops@airticket.com>"
LABEL description="Air Ticket Booking System - Build Stage"

WORKDIR /build

# Copy pom.xml first to leverage Docker layer caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="DevOps Team <devops@airticket.com>"
LABEL description="Air Ticket Booking System - Runtime"
LABEL version="1.0.0"

# Install wget for health checks
RUN apk add --no-cache wget

# Create non-root user for security
RUN addgroup -S airticketapp && adduser -S airticketuser -G airticketapp

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /build/target/airticket-booking-system.jar app.jar

# Change ownership
RUN chown -R airticketuser:airticketapp /app

# Switch to non-root user
USER airticketuser

# Expose application port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/flights/health || exit 1

# Set JVM options
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
