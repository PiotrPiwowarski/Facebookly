FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ./target/facebookly-2.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]