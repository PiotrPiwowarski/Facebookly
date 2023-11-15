# Facebookly

This is a social networking API written in Java/Spring Boot

# Docker Image
```
https://hub.docker.com/r/piotrpiw/facebookly-api
```

# Docker Compose File
```
version: "3.0"
services:

  database:
    container_name: facebookly-database
    image: mysql:8.0.33
    ports:
      - "3309:3306"
    networks:
      - facebookly-network
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: facebookly

  api:
    container_name: facebookly-api
    image: piotrpiw/facebookly-api:1.0
    ports:
      - "8080:8080"
    networks:
      - facebookly-network
    restart: always
    depends_on:
      - database
    environment:
      MYSQL_HOST: facebookly-database
      MYSQL_PASSWORD: root
      MYSQL_USERNAME: root
      MYSQL_PORT: 3306
      MYSQL_DATABASE: facebookly

networks:
  facebookly-network:
    driver: bridge

volumes:
  facebookly-data:
```

You can change some of environment variables, for example MYSQL_ROOT_PASSWORD

You can also switch mode between 'dev' and 'prod' using SPRING_PROFILE_ACTIVE, default 'prod' mode

'prod' mode is using MySQL database and 'dev' mode H2 built in database with example set of data which you can change in [data](/src/main/resources/data.sql) file


# Quickstart

Go to directory with Docker Compose file and then use the command below:
```
docker compose up
```

# API Documentation

Run application and go to:
```
/swagger-ui.html
```
