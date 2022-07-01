## 3) Docker

In this chapter the concept of Docker in software development as well as its usage is discussed.

Docker is a software enabling developers to containerize their application. These docker images can then be shared and
run on all environments supporting these images, completely independent of the operating system used.

To create a Docker image of your application a so-called Dockerfile needs to be created, telling Docker what to do.
The Dockerfile of this application looks as follows:

```
# Maven build
FROM openjdk:17 as builder

WORKDIR /spring

COPY .mvn/ ./.mvn/
COPY mvnw ./
COPY pom.xml ./

RUN ./mvnw dependency:resolve dependency:resolve-plugins

COPY src/ src/
RUN ./mvnw package -DskipTests



# Base image
FROM openjdk:17

EXPOSE 8080

COPY --from=builder /spring/target/SpringBootWeather-0.0.1-SNAPSHOT.jar springbootweather.jar

ENTRYPOINT ["java", "-jar", "springbootweather.jar"]
```

In the first half, commented as "Maven build", Docker is told to use the base image of openjdk:17. With this then a working
directory is chosen and the necessary maven files are copied, telling Docker the important parts of the project
configuration. With this, docker is capable of resolving maven dependencies and to package a new artifact, using maven itself.
Therefore, this part of the Dockerfile (when executed) packages the project contents in a new .jar-File.

After this .jar file is created it is used in the second half, denoted as "Base image". Here again, docker chooses the java
jdk:17 base image. The following lines copy the created artifact into our docker image and start it whenever the docker 
image is run, clarified by the ENTRYPOINT.
EXPOSE 8080 then clarifies that your localhost:8080 will make the docker image accessible via your browser of choice at
[Localhost:8080](localhost:8080).

To create a docker image of this project open a command line (or a command line tool of your choice) and go to this project 
directory `/SpringBootWeather`. Inside this folder then use the command

```
docker build -t springbootweather:latest
```

This will create a docker image named "springbootweather" with an additional "latest"-tag, according to the given Dockerfile.
To check that all worked correctly, run the command:

```
docker images
```

to retrieve all docker images you have locally. Alternatively, you can directly run the docker image by executing:

```
docker run -p 8080:8080 springbootweather
```

This will then start the docker image you just created. But don't be happy to early, you will probably see upcoming
error messages. This is based on the fact, that so far no database was created for this application even it needs one.
To solve this problem go to the next chapter.

---

Last chapter: [Chapter 02 - Structure of code](chapter-2.md)

Next chapter: [Chapter 04 - Docker-Compose](chapter-4.md)