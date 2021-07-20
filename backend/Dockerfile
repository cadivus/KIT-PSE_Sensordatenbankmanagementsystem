FROM maven:3.8.1-openjdk-16 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -e -f /usr/src/app/pom.xml clean package spring-boot:repackage

FROM openjdk:16
COPY --from=build /usr/src/app/target/backend-0.0.1-SNAPSHOT.jar /usr/app/backend-1.0.0-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/app/backend-1.0.0-SNAPSHOT.jar"]
