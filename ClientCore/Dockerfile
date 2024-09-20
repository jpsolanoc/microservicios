FROM openjdk:17-slim
VOLUME /tmp
EXPOSE 8081
ARG JAR_FILE=build/libs/ClientCore-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]