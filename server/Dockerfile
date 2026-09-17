FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY build/libs/server-all.jar app.jar

EXPOSE 48067/tcp
EXPOSE 48068/udp

ENTRYPOINT ["java", "-jar", "app.jar"]