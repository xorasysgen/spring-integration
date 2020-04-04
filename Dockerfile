FROM openjdk:8
EXPOSE 8080
ADD target/spring-integration-run.jar spring-integration-run.jar
ENTRYPOINT ["java", "-jar","/spring-integration-run.jar"]