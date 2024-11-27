FROM amazoncorretto:21-alpine3.19
VOLUME /var/log/
VOLUME /root/.gradle
COPY . /app/
RUN chown -R root /app/
USER root
RUN /app/gradlew build -p /app/ -x test
CMD ["java", "-jar", "/app/build/libs/ubots-test-0.0.1-SNAPSHOT.jar"]