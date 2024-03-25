FROM openjdk:17
VOLUME /tmp
EXPOSE 8761
ADD ./target/ms-eureka-server-0.0.1-SNAPSHOT.jar ms-eureka-server.jar
ENTRYPOINT ["java","-jar","/ms-eureka-server.jar"]