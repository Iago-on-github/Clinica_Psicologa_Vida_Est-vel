FROM openjdk:17-alpine

# MAINTAINER github/Iago-on-github

COPY ./target/demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1-SNAPSHOT.jar

WORKDIR /app

ENV SPRING_FLYWAY_ENABLED=false

ENTRYPOINT ["java", "-Dspring.flyway.enabled=false", "-jar", "demo-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
