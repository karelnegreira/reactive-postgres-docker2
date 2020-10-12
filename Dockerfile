FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
ADD target/java-router-postgres.jar java-router-postgres.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/java-router-postgres.jar"]