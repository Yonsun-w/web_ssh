FROM openjdk:8-jdk-alpine

VOLUME /tmp /test

COPY ./target/WebSSH.jar commo-sonarsync-job.jar

EXPOSE 80

ENTRYPOINT ["java","-jar","/commo-sonarsync-job.jar"]