FROM gradle:6.9-jdk8 AS build
#COPY --chown=gradle:gradle ./home/gradle/src
#WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:8-jre-slim

RUN mkdir /app

#COPY --from=build /home/gradle/src/build/libs/*.jar /app/rest-app-example-1.0-SNAPSHOT.jar
COPY --from=build/home/gradle/src/build/libs/rest-app-example-1.0-SNAPSHOT.jar /app/rest-app-example-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap","-jar","/app/rest-app-example-1.0-SNAPSHOT.jar"]

