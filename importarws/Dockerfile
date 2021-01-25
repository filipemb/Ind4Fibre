FROM openjdk:8

COPY target/importarws-0.0.1.jar importarws.jar

EXPOSE 8081

ENV JAVA_OPTS ""

CMD java ${JAVA_OPTS} -jar importarws.jar