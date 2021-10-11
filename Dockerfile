FROM gradle:gradle-6.8-bin AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:8-jre-slim

EXPOSE 80

RUN mkdir /app

#COPY --from=build /home/gradle/src/build/libs/*.jar /app/rest-app-example-1.0-SNAPSHOT.jar
COPY --from=build /home/gradle/src/build/libs/rest-app-example-1.0-SNAPSHOT.jar /app/rest-app-example-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap","-jar","/app/rest-app-example-1.0-SNAPSHOT.jar"]

