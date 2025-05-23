FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean install

EXPOSE 9090

CMD ["java", "-jar", "target/xpvault-0.0.1-SNAPSHOT.jar"]
