FROM openjdk:13-alpine
MAINTAINER Killian Nolan
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
COPY target/volunteer-service-0.0.1-SNAPSHOT.jar volunteer-docker-1.0.0.jar
ENTRYPOINT ["java","-jar","/volunteer-docker-1.0.0.jar"]

