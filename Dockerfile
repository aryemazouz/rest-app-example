FROM gradle:6.9-jdk8 AS build
RUN gradle build --no-daemon
FROM openjdk:8-jre-slim

RUN mkdir /app
RUN mkdir /conf

ARG JAR_FILE=rest-app-example-1.0-SNAPSHOT.jar
COPY ./build/libs/${JAR_FILE} /app/rest-app-example.jar

#ENTRYPOINT exec java -jar $JAVA_OPTS  /app/rest-app-example.jar
#ENTRYPOINT ["java ${JAVA_OPTS}","-jar", "/app/rest-app-example.jar"]
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/rest-app-example.jar ${0} ${@}"]