FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar application.jar

RUN rm -rf /app/src /app/pom.xml /app/target

RUN apk update && apk add --no-cache  \
    mysql-client \
    freetype \
    fontconfig \
    font-noto


EXPOSE 8080
ENTRYPOINT ["java", "-Xms512m", "-Xmx3584m", "-Djava.awt.headless=true", "-jar", "application.jar"]
