FROM eclipse-temurin:21-jre-jammy AS lightapp
WORKDIR /app

RUN groupadd -g 1000 head && \
    useradd -u 1000 -g head -ms /bin/bash head

COPY --chown=head:head build/libs/messenger-min.jar app.jar

USER head

EXPOSE 4052

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=80.0", "-jar", "app.jar"]