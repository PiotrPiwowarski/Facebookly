# Facebookly

This is a social networking API written in Java/Spring Boot

# Docker Image
```
https://hub.docker.com/r/piotrpiw/facebookly-api
```

# Docker Compose File

[docker compose file](/)

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
