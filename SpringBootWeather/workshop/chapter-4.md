## 4) Docker-Compose & Databases

In this chapter docker-compose and its possibilities for multi-container applications are discussed.

### Databases in Spring Boot

The moment you will open the pom.xml file, a file containing the project information used by maven. You will probably find 
dependencies looking like:

```
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
        </dependency>
    </dependencies>
```

Next to general dependencies as the `spring-boot-starter-web` and `spring.boot-starter-test`, necessary for spring web
applications in general, there are two more dependencies added manually. These dependencies `spring-boot-starter-data-jpa`
as well as `postgresql` are added to enable the application communication with a postgres database.

These dependencies in combination with the model "Weather" created, will create a database for this project containing
the weather data with attributes defined in the model itself. To achieve this jpa uses the @Entity and @Id flags.

### Docker-compose 

According to that, our application is now capable of communicating with an external database, but where is it? This is the
part where docker-compose comes in handy. Docker-compose enables developers to create multi-container applications of a single 
application. To create such an application a "docker-compose.yml" file is created, just as the Dockerfile before.

Looking in the docker-compose.yml of this project:

```
version: '3.5'

services:
  db:
    image: postgres:12
    container_name: ${DB_CONTAINER_NAME}
    restart: always
    environment:
      POSTGRES_USER: ${DB_USERNAME}
      POSTGRES_PASSWORD: ${DB_PASSWORD}
      POSTGRES_DB: ${DB_TITLE}
    ports:
      - "${DB_LOCAL_PORT}:${DB_DOCKER_PORT}"
  app:
    depends_on:
     - db
    build: .
    ports:
     - "${APP_LOCAL_PORT}:${APP_DOCKER_PORT}"
    environment:
      SPRING_DATASOURCE_URL : jdbc:postgresql://db:${DB_DOCKER_PORT}/${DB_TITLE}
volumes:
  postgre_db:
    driver: local
```

we find a structure looking like above. Elements given as `${VARIABLE_NAME}` are variables,
which are defined in an external file, named `.env` in the same folder as our docker-compose.yml.

First, the docker-compose.yml version is defined.
Then two different services "db" and "app" are described. "db" will create the postgres database, while "app" will create
our application. 

For the database an online docker image is used, which is pulled if it is not already given locally on your device.
Afterwards, a container name is set, next to environments for the database. Same happens for the application itself.
Differences are the `depends_on` attribute, which keeps the "app"-container from starting until the database is fully created.

To run your multi-container application, open your command line at the folder `/SpringBootWeather` and execute the command:

```
docker-compose up -d --build
```

After this you should be able to open your browser at [Localhost:8081](localhost:8081) and see the interface of this application.
To check if your docker containers are running correctly, you can also execute:

```
docker-compose ps
```

and to stop your containers:

```
docker-compose down
```

---
Last chapter: [Chapter 03 - Docker](chapter-3.md)

Next chapter: [Chapter 05 - Kubernetes](chapter-5.md)