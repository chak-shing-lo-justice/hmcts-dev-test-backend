FROM gradle:8.14.1-jdk17 AS build
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY build.gradle $APP_HOME
COPY src $APP_HOME/src
COPY gradle $APP_HOME/gradle

RUN gradle clean build

RUN ls $APP_HOME/

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
COPY --from=build /usr/app/build/libs/test-backend.jar /opt/app/app.jar

EXPOSE ${SERVER_PORT}

ENTRYPOINT [ "java", "-jar", "app.jar" ]
