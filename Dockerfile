FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER Saurabh Singh

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/

ENV BUILD_VAR heroku
RUN echo "ENV : ${BUILD_VAR}"
RUN mvn package -DskipTests -DENV=${BUILD_VAR}

FROM amazoncorretto:11-alpine-jdk
ARG JAR_FILE=/build/target/*.jar

WORKDIR /app
COPY --from=MAVEN_BUILD ${JAR_FILE} app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]