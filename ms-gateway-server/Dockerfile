FROM openjdk:17
VOLUME /tmp
EXPOSE 9081
ADD ./target/ms-gateway-server-0.0.1-SNAPSHOT.jar ms-gateway-server.jar
ENTRYPOINT ["java","-jar","/ms-gateway-server.jar"]