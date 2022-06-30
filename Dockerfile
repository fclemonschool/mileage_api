FROM gradle:jdk-alpine AS builder
RUN java -version

COPY . /usr/src/mileage/
WORKDIR /usr/src/mileage/
RUN gradle build --no-daemon

FROM openjdk:8-jre-alpine
WORKDIR /root/
COPY --from=builder /usr/src/mileage/build/libs/mileage-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./app.jar"]
