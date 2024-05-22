FROM registry-docker.apps.eul.sncf.fr/hub/eclipse-temurin:21-jre-alpine 

COPY target/*.jar app.jar

CMD java -XshowSettings:vm -version && java -jar app.jar

EXPOSE 8080

