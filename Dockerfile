FROM openjdk:11
EXPOSE 8080
ADD target/covid-19-tracker.jar covid-19-tracker.jar
ENTRYPOINT ["java","-jar","/covid-19-tracker.jar"]