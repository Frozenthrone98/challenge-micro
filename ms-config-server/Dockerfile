FROM openjdk:17
VOLUME /tmp
EXPOSE 9000
ADD ./target/ms-config-server-0.0.1-SNAPSHOT.jar ms-config-server.jar
ENTRYPOINT ["java","-jar","/ms-config-server.jar"]