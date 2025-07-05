FROM openjdk:19-jdk-slim
LABEL authors="Home-PC"

EXPOSE 5500
ARG PATH=target/MoneyTransferService-0.0.1-SNAPSHOT.jar

ADD ${PATH}  app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]