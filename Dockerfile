FROM openjdk:17 as builder

WORKDIR /spring

COPY .mvn ./.mvn/
COPY mvnw ./
COPY pom.xml ./

RUN ./mvnw dependency:resolve dependency:resolve-plugins

COPY src src/
RUN ./mvnw package -DskipTests


# base image
FROM gcr.io/distroless/java17-debian11

# port
EXPOSE 8080

# jar file
COPY --from=builder /spring/target/cloud-native-weather-spring-0.0.1-SNAPSHOT.jar springbootweather.jar

# entry
CMD ["springbootweather.jar"]