# VolunteerService

A spring boot application to implement the core components of a job volunteer system.

# Required

Java 11

# Running the application locally

Since this application is integrated with a postgres DB, to run locally you will need a postgres instance running. The best way would be to build and run the docker images as instructed in the next section.

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in
the VolunteerServiceApplication.java class from your IDE. Passing in profile as dev.

Or you can build with maven

```mvn install```

Once built:
```java -Dspring.profiles.active=dev -jar target/volunteer-service-0.0.1-SNAPSHOT.jar```

Alternatively you can use the Spring Boot Maven plugin like so:

```mvn spring-boot:run```

# Running the Docker container
The application is docker containerized along with the postgres DB, as outlined in the Dockerfile and docker-compose.yml.

Package the application
```mvnw clean package```

Docker compose build and run
```docker-compose build && docker-compose up```

Application will be running on http://localhost:9000/.

# Deployment

Application has been deployed to Heroku at:
```https://volunteer-service-9d0da4f8254d.herokuapp.com/```

# Documentation

Swagger doc is available at;
```http://localhost:900/swagger-ui.html``` (When docker container is running) </br>
```https:/volunteer-service-9d0da4f8254d.herokuapp.com/swagger-ui.html```




