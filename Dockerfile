FROM adoptopenjdk/openjdk11
MAINTAINER davidrodriguezlosada@gmail.com
COPY target/pilotes-ordering-1.0.0-SNAPSHOT.jar pilotes-ordering-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/pilotes-ordering-1.0.0-SNAPSHOT.jar"]