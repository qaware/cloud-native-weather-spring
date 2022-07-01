# Workshop Applications

### 1) SpringBootHelloWorld

SpringBootHelloWorld is a short introduction into the world of spring boot and should help you to set up your first primitive 
spring boot application. After you successfully created this (in this case) web application, you are introduced to the concept
Dockerfiles and containerizing your application.

To start the SpringBootHelloWorld application either go to SpringBootHelloWorld -> sry -> main -> java -> de.qaware.springboothelloworld
and run the SpringBootHelloWorldApplication or create a local docker image, by opening your command line in SpringBootHelloWorld,
use Maven to package the project and run the commands:

```
docker build -t springhelloworld:latest 
```
```
docker run -p 8080:8080 run springhelloworld
```

The app should be accessible at your [localhost:8080](localhost:8080).

### 2) SpringBootWeather

SpringBootWeather is supposed to introduce you further into the world of docker and containerizing as a cloud native concept.
This application uses concepts used for 12 Factor apps to guide you through using multi-container configurations, kubernetes
as well as microservices like Tilt to make your life as a developer easier. 

To start SpringBootWeather on a local kubernetes cluster once you worked through the short tutorial given in the ReadMe, 
type once you reach the SpringBootWeather folder in your command line:

```
tilt up
```

and the application should be accessible at [localhost:8081](localhost:8081)

Alternatively start the application as a local multi-container by running the command:

```
docker-compose up --build
```

or start the database manually (as a image on port 5432 or by using the green arrow in the docker compose file) and run
the application itself at SpringBootWeather -> sry -> main -> java -> de.qaware.springbootweather.