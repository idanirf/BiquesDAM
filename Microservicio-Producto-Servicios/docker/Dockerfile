FROM gradle:7-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon
FROM openjdk:17-jdk-slim-buster
EXPOSE 8383:8383
RUN mkdir /app
# RUN mkdir /cert
# COPY --from=build /home/gradle/src/cert/* /cert/
# TODO Comprobar ruta
COPY --from=build /home/gradle/src/build/libs/microservicios-producto-servicios-all.jar /app/microservicios-producto-servicios.jar
ENTRYPOINT ["java","-jar","/app/microservicios-producto-servicios.jar"]