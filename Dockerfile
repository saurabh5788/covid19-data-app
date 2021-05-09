FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER Saurabh Singh
COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package -DskipTests

FROM amazoncorretto:11-alpine-jdk

WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/covid19-data-app.jar /app/
ENTRYPOINT ["java", "-jar", "covid19-data-app.jar"]