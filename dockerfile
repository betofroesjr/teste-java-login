########Maven build stage########
FROM maven:3.8.4-openjdk-17 as maven_build
ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/.m2 mvn -f $HOME/pom.xml clean install -DskipTests=true

########JRE run stage########
from openjdk:17-oracle
ARG JAR_FILE=teste-java-login-0.0.1-SNAPSHOT.jar

WORKDIR /app

COPY --from=maven_build /app/target/${JAR_FILE} /app/
ENTRYPOINT ["java", "-jar", "teste-java-login-0.0.1-SNAPSHOT.jar"]
EXPOSE 8082