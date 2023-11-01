FROM tomcat:9.0.55-jdk8-temurin

ADD BiblioRegistry.war /usr/local/tomcat/webapps/

RUN mkdir /webApp_resources

ADD arial.ttf /webApp_resources/

EXPOSE 8080

CMD ["catalina.sh", "run"]

