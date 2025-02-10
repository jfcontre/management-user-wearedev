FROM maven:3.9.4-amazoncorretto-17 AS builder
WORKDIR /app
COPY pom.xml /app/
RUN mvn dependency:go-offline

COPY src /app/src
RUN mvn clean package
FROM amazoncorretto:17
WORKDIR /app
COPY --from=builder /app/target/usermanagement-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]