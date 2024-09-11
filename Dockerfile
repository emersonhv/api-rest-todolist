# Imagen base OpenJDK
FROM openjdk:17

# Crear un volumen
VOLUME /tmp

# Directorio de trabajo
WORKDIR /app

# Copia el archivo JAR al directorio anterior
COPY ./target/todolist-0.0.1-SNAPSHOT.jar api.jar

# Puerto de ejecución de api
EXPOSE 8080

# Comandos de ejecucion
ENTRYPOINT ["java", "-jar", "api.jar"]
