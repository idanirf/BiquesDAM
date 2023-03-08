FROM gradle:7-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim-buster
EXPOSE 8383
RUN mkdir /app
# RUN mkdir /cert
# COPY --from=build /home/gradle/src/cert/* /cert/
# TODO Comprobar ruta
COPY --from=build /home/gradle/src/build/libs/Microservicio-Producto-Servicios-0.0.1-SNAPSHOT.jar /app/microservicios-productos.jar
ENTRYPOINT ["java","-jar","/app/microservicios-productos.jar"]