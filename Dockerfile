FROM --platform=linux/amd64 openjdk:17.0.1-jdk-slim

RUN apt-get -y update
RUN apt -y install wget
RUN apt -y install unzip
RUN apt -y install curl

# google chrome 설치
RUN wget https://chrome-versions.com/google-chrome-stable-114.0.5735.106-1.deb
#RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt-get -y update
RUN apt -y install ./google-chrome-stable-114.0.5735.106-1.deb
#RUN apt -y install ./google-chrome-stable_current_amd64.deb

# chromedriver 설치
RUN wget -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/` curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE_114`/chromedriver_linux64.zip
RUN unzip /tmp/chromedriver.zip chromedriver -d /usr/local/bin/
RUN chmod +x /usr/local/bin/chromedriver

# chromedriver를 PATH에 추가
ENV PATH="/usr/local/bin:${PATH}"

WORKDIR /app

COPY . .

RUN ./gradlew clean bootJar

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

# Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080

