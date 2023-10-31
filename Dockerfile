FROM openjdk:17
COPY target/Facebookly-0.0.1-SNAPSHOT.jar Facebookly-1.0.jar
ENTRYPOINT ["java", "-jar", "/Facebookly-1.0.jar"]