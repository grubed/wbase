FROM openjdk:8-jdk-alpine
ADD target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-server","-Xms256m","-Xmx512m","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

