FROM openjdk:11

#RUN yum -y install procps-ng.x86_64
WORKDIR /

ARG APP_BIN=urlShorten-0.0.1-SNAPSHOT.jar
ARG SERVER_PORT=8990

ENV DB_SERVICE=db:3306

EXPOSE $SERVER_PORT

COPY ./wait-for-it.sh ./
COPY ./url-shorten-entry.sh ./
# COPY ./server.crt ./

ENTRYPOINT ["./url-shorten-entry.sh"]

COPY target/$APP_BIN ./url-shorten.jar