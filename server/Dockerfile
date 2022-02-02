FROM java:8
ARG JAR_FILE=build/libs/ivblanc-api-module-0.0.1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 9999