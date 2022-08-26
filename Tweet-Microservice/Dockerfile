FROM openjdk:11
EXPOSE 9004
ADD target/tweet.jar tweet.jar
ENTRYPOINT ["java","-jar","/tweet.jar"]