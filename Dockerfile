FROM openjdk:17-jdk
COPY target/facebookly.jar .
COPY src/main/resources/init.sql /docker-entrypoint-initdb.d/
EXPOSE 8080
CMD mvn clean install
ENTRYPOINT ["java", "-jar", "/facebookly.jar"]