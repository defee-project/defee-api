FROM openjdk:17

WORKDIR /app

#./gradlew clean bootJar 후 실행
ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

# Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080

