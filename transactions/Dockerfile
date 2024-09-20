FROM openjdk:17-slim
VOLUME /tmp
EXPOSE 8082
ARG JAR_FILE=build/libs/transactions-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]