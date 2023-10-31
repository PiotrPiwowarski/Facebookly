FROM openjdk:17
COPY target/facebookly-1.0.jar facebookly-1.0.jar
ENTRYPOINT ["java", "-jar", "/facebookly-1.0.jar"]