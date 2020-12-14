# syntax = docker/dockerfile:experimental
FROM gradle:6.6.0-jdk8 AS cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
RUN mkdir -p /home/gradle/build_tmp
COPY build.gradle.kts settings.gradle.kts /home/gradle/build_tmp
WORKDIR /home/gradle/build_tmp
RUN gradle clean build -i --stacktrace

FROM gradle:6.6.0-jdk8 AS build
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY --chown=gradle:gradle . /home/gradle/build_home
WORKDIR /home/gradle/build_home
RUN gradle shadowJar -i --stacktrace

FROM openjdk:8-jre
RUN mkdir /services
COPY --from=build /home/gradle/build_home/build/libs/fetcher-all.jar /services/fetcher.jar

RUN GRPC_HEALTH_PROBE_VERSION=v0.3.2 && \
    wget -qO/bin/grpc_health_probe https://github.com/grpc-ecosystem/grpc-health-probe/releases/download/${GRPC_HEALTH_PROBE_VERSION}/grpc_health_probe-linux-amd64 && \
    chmod +x /bin/grpc_health_probe

USER 1000
ENTRYPOINT ["java",  "-jar", "/services/fetcher.jar"]
