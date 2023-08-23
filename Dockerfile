FROM openjdk:13-alpine
MAINTAINER Killian Nolan
ARG DEPENDENCY=target/dependency
COPY target/volunteer-service-0.0.1-SNAPSHOT.jar volunteer-docker-1.0.0.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=dev", "/volunteer-docker-1.0.0.jar"]

