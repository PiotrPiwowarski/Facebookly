FROM openjdk:17-jdk
COPY target/facebookly.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/facebookly.jar"]