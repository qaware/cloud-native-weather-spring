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
COPY --from=builder /spring/target/cloud-native-weather-spring.jar cloud-native-weather-spring.jar

# entry
CMD ["cloud-native-weather-spring.jar"]