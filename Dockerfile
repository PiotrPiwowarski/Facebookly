FROM openjdk:17-jdk
COPY target/facebookly.jar .
EXPOSE 8080
CMD mvn clean install
ENTRYPOINT ["java", "-jar", "/facebookly.jar"]