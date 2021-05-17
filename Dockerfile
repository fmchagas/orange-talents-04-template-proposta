## Builder Image
FROM maven:3.8.1-openjdk-11-slim AS builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

## Runner Image
FROM openjdk:11.0.11-jre-slim
RUN addgroup spring && useradd spring -g spring
USER spring:spring
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} /usr/app/app.jar
COPY --from=builder /usr/src/app/target/proposta.jar /usr/app/app.jar
ENTRYPOINT ["java","-Xmx512m","-Dserver.port=${PORT}","-Dfile.encoding=UTF-8","-jar","/usr/app/app.jar"]