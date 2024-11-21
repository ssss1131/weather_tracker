FROM tomcat:10.1.10-jdk17

WORKDIR /usr/local/tomcat

RUN rm -rf webapps/*

COPY ROOT.war webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]