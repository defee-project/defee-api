FROM openjdk:17

WORKDIR /app

COPY target/defee.jar /app/defee.jar

# Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "defee.jar"]

EXPOSE 8080

