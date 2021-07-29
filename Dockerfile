FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} userauth-service.jar
ENTRYPOINT ["java","-jar","/userauth-service.jar"]
EXPOSE 9002