#FROM openjdk:13-alpine
#MAINTAINER Killian Nolan
##RUN addgroup -S spring && adduser -S spring -G spring
##USER spring:spring
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#COPY target/volunteer-service-0.0.1-SNAPSHOT.jar volunteer-docker-1.0.0.jar
#ENTRYPOINT ["java","-jar","/volunteer-docker-1.0.0.jar"]

# Docker multi-stage build

# 1. Building the App with Maven
FROM maven:3-jdk-11

ADD . /volunteer-service
WORKDIR /volunteer-service

# Just echo so we can see, if everything is there :)
RUN ls -l

# Run Maven build
RUN mvn clean install


# 2. Just using the build artifact and then removing the build-container
FROM openjdk:11-jdk

MAINTAINER Killian Nolan

VOLUME /tmp

# Add Spring Boot app.jar to Container
COPY --from=0 "volunteer-service/target/volunteer-service-0.0.1-SNAPSHOT.jar" volunteer-docker-1.0.0.jar

# Fire up our Spring Boot app by default
CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /volunteer-docker-1.0.0.jar" ]