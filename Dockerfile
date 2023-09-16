#FROM ubuntu:latest
#LABEL authors="ifeanyichukwuobeta"
#
#ENTRYPOINT ["top", "-b"]


FROM maven:3.8.5-openjdk-17 AS build
#FROM maven:3.9.4-amazoncorretto-17-al2023 AS build

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build  /target/Banking Backend Services-0.0.1-SNAPSHOT.jar bankingServices.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]