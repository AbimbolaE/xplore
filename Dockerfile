FROM openjdk:latest
COPY application/target/universal/stage /app
ENTRYPOINT ["/app/bin/application"]
EXPOSE 8080
