from openjdk:17-oracle
MAINTAINER github/betofroesjr
COPY ./target/teste-java-login-0.0.1-SNAPSHOT.jar /app/teste-java-login-0.0.1-SNAPSHOT.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "teste-java-login-0.0.1-SNAPSHOT.jar"]
EXPOSE 8082