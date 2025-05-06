# Usa una imagen base de Java
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor
COPY target/sgrh-0.0.1-SNAPSHOT.jar /app/app.jar

# Define el comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/app.jar"]