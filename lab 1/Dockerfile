FROM maven:3.9.7-amazoncorretto-17 AS builder
WORKDIR /opt/app
COPY . .

RUN ["mvn", "clean", "package", "-Dmaven.test.skip"]

FROM bellsoft/liberica-openjdk-debian:17
WORKDIR /opt/app

COPY --from=builder /opt/app/target/lab1.jar .

ENV SERVER_PORT=8067

ENTRYPOINT ["java","-jar","lab1.jar"]