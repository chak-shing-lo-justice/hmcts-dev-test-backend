FROM eclipse-temurin:17-jre-jammy as final

# Create a non-privileged user that the app will run under.
# See https://docs.docker.com/go/dockerfile-user-best-practices/
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

WORKDIR /opt/app/

# Copy the executable
COPY build/libs/test-backend.jar /opt/app/app.jar

EXPOSE ${SERVER_PORT}

ENTRYPOINT [ "java", "-jar", "app.jar" ]
