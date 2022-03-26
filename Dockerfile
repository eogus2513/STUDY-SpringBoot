FROM openjdk:11-jdk-alpine

EXPOSE 8080
ENV TZ=Asia/Seoul

ADD build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]