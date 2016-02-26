FROM java:8
ADD brew-find-vt-0.1.0.jar app.jar
DOCKER_HOST=localhost:8080
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]