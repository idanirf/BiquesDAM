FROM gradle:7-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle buildFatJar --no-daemon
FROM openjdk:17-jdk-slim-buster
EXPOSE 6969:6969
RUN mkdir /app
RUN mkdir /cert
COPY --from=build /home/gradle/src/cert/* /cert/
COPY --from=build /home/gradle/src/build/libs/ApiGateway-all.jar /app/microservicios-gateway.jar
ENTRYPOINT ["java","-jar","/app/microservicios-gateway.jar"]