FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean install -DskipTests

EXPOSE 9090

CMD ["java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]
