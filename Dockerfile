FROM openjdk:11
ADD target/*.jar app.jar
LABEL name="ticket-order" \
      version="latest"
ENTRYPOINT ["java","-jar", "app.jar"]