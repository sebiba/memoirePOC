#FROM maven:3-openjdk-11
#WORKDIR /usr/src/app
#COPY pom.xml .
#COPY src ./src
#RUN mvn package
#CMD ["java","-jar","./target/thesis-0.0.1-SNAPSHOT.jar"]
#EXPOSE 8080

FROM mysql:latest
ENV MYSQL_ROOT_PASSWORD=root
# Add a database
ENV MYSQL_DATABASE memoire
# needed for intialization
# Add the content of the sql-scripts/ directory to your image
# All scripts in docker-entrypoint-initdb.d/ are automatically
# executed during container startup
COPY ./database_schema /docker-entrypoint-initdb.d/

