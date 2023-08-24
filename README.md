# VolunteerService

A spring boot application to implement the core components of a job volunteer system.

# Required

Java 11

# Running the application locally

Since this application is integrated with a postgres DB, to run locally you will need a postgres instance running. The
best way would be to build and run the docker images as instructed in the next section.

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in
the VolunteerServiceApplication.java class from your IDE. Passing in profile as dev.

Or you can build with maven

```mvn install```

Once built:
```java -Dspring.profiles.active=dev -jar target/volunteer-service-0.0.1-SNAPSHOT.jar```

Alternatively you can use the Spring Boot Maven plugin like so:

```mvn spring-boot:run```

# Running the Docker container
Docker Engine needs to be started locally in order to run containers.
The application is docker containerized along with a postgres DB image pulled from the docker public repo, as outlined
in the Dockerfile and docker-compose.yml.

Package the application
```mvn clean package```

Docker compose build and run
```docker compose build && docker compose up```

Application will be running on http://localhost:9000/.

# Deployment

Application has been deployed to Heroku at:
```https://volunteer-service-9d0da4f8254d.herokuapp.com/```

# Documentation

Swagger doc is available at;</br>

- ```http://localhost:9000/swagger-ui.html``` (When docker container is running) </br>
- ```https:/volunteer-service-9d0da4f8254d.herokuapp.com/swagger-ui.html```

# Data

The data has been loaded by new endpoints i have exposed, as shown in the swagger doc. We have 3 endpoints now that can

- Import Jobs by CSV
- Import Volunteers by CSV
- Import Jobs/Volunteers relationships by CSV

The tabs on the provided excel sheet should be saved individually as CSV files and imported through the relevant
endpoints.</br>
The heroku application has this data already loaded in. The local docker instance will need this data to be imported.

# Observations/Improvements
1) The primary key for job/volunteer entities does not auto-incremement and is not auto generated, for the purpose of this assignment this needs to be set when creating as all the information is on the excel file with ID's already set. If we set auto generate here, then we would need to update the jobs-volunteers csv tab with updated id's.
2) Importing the entire excel book would be a key improvement rather than splitting into CSV files. With more time this is something I would add.


