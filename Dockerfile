FROM eclipse-temurin:21-jre
COPY spring-boot-rest/target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]