#FROM openjdk:11.0.6-jre
#ARG CODE_VER
#ENV VERSION=${CODE_VER}
#RUN mkdir /opt/app
#COPY ./target/mancala-0.0.1-SNAPSHOT.jar /opt/app
#WORKDIR /opt/app
#ARG JAVA_OPTIONS="-Dfile.encoding=UTF-8 -Dproduct.version.version=${VERSION}"
#ENV JAVA_OPTS=${JAVA_OPTIONS}
#EXPOSE 8080
#CMD ["sh","-c","java ${JAVA_OPTS} -jar mancala-0.0.1-SNAPSHOT.jar"]

FROM openjdk:17-jdk-alpine3.14
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]