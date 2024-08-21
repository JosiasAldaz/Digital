#inicia con la imagen base que contiene Java runtime
FROM openjdk:17-jdk-alpine

# se cambia el jar del microservicio a imagen
COPY target/Zapatos_backend-0.0.1-SNAPSHOT.jar Zapatos_backend-0.0.1-SNAPSHOT.jar

#se ejecuta el microservicio
ENTRYPOINT ["java","-jar","/Zapatos_backend-0.0.1-SNAPSHOT.jar"]