# Imagen base con Java 21
FROM openjdk:21-jdk-slim as builder

# Establecer el directorio de trabajo en /app
WORKDIR /app

# Copiar el archivo JAR generado de la aplicación
COPY target/seguridad-calidad-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java","-jar","app.jar"]

# Comando para construir la imagen
# docker build --no-cache -t app-seguridad .

# Comando para ejecutar la imagen
# docker run -d -p 8080:8080 app-seguridad