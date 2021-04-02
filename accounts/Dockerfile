FROM adoptopenjdk/openjdk11:alpine
ARG JAR_FILE=target/*.jar
ARG CREDENTIALS_FILE=good-habits-app.json
COPY ${JAR_FILE} app.jar
COPY ${CREDENTIALS_FILE} app.json
ENV GOOGLE_APPLICATION_CREDENTIALS="/app.json"
ENTRYPOINT ["java","-jar","/app.jar"]