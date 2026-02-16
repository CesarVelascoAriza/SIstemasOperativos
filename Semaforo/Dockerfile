# Use a lightweight JRE for Java 24
FROM eclipse-temurin:24-jre

# version bump 1.1.0
# install Xvfb so GUI can run headlessly inside container
RUN apt-get update && \
    apt-get install -y xvfb libxi6 libxrender1 libxtst6 && \
    rm -rf /var/lib/apt/lists/*

# working directory inside container
WORKDIR /app

# copy the built jar (build with mvn package before docker build)
# the jar is produced with a fixed name thanks to <finalName> in pom
COPY target/semaforo.jar /app/semaforo.jar

# run the application
ENTRYPOINT ["sh","-c","xvfb-run java -jar /app/semaforo.jar"]
